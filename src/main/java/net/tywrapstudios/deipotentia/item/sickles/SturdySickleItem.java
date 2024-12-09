package net.tywrapstudios.deipotentia.item.sickles;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.registry.DTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SturdySickleItem extends HoeItem {
    public SturdySickleItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 4, -1.5f, settings.rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int chanceToPush = target.getRandom().nextBetween(0, 5);
        if (target.getBlockStateAtPos().isIn(DTags.Blocks.NON_STURDY.get()) && chanceToPush != 0) {
            target.updatePosition(target.getX(), target.getY()-1.5d, target.getZ());
        } else if (chanceToPush == 0) {
            World world = attacker.getWorld();
            double impactRadius = 6.0;
            double generalPushStrength = 2.0;
            world.playSound(attacker.getX(), attacker.getY(), attacker.getZ(),
                    SoundEvents.ENTITY_PHANTOM_SWOOP,
                    SoundCategory.NEUTRAL,
                    1.0f, 1.0f, false);

            for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, attacker.getBoundingBox().expand(impactRadius), entity -> entity != attacker)) {
                if (entity != attacker && entity.squaredDistanceTo(attacker) <= impactRadius * impactRadius) {
                    Vec3d direction = entity.getPos().subtract(attacker.getPos()).normalize();
                    entity.setVelocity(direction.multiply(generalPushStrength));
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        float currentDamage = user.getMaxHealth() - user.getHealth();
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.BLOCK_BEACON_ACTIVATE,
                SoundCategory.NEUTRAL,
                0.7F,
                2F / (world.random.nextFloat() * 0.4F + 0.8F)
        );
        user.getItemCooldownManager().set(this, 1200);
        user.heal(currentDamage / 2);
        user.getStackInHand(hand).damage(2, user, playerEntity -> playerEntity.sendToolBreakStatus(hand));

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.sturdy_sickle").formatted(Formatting.ITALIC, Formatting.GOLD));
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.sturdy_sickle.sec").formatted(Formatting.ITALIC, Formatting.DARK_GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
