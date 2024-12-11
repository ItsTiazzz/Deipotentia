package net.tywrapstudios.deipotentia.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.util.EntityVelocityManipulation;
import net.tywrapstudios.deipotentia.util.NbtUtilities;
import net.tywrapstudios.deipotentia.util.TickScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ValSoulStranglerItem extends Item {
    public ValSoulStranglerItem(Settings settings) {
        super(settings.rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack T = user.getStackInHand(hand);
        if (user.isSneaking()) {
            boolean current = NbtUtilities.toggleEnabledForStack(T);
            if (!current) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.BLOCK_BEACON_ACTIVATE,
                        SoundCategory.NEUTRAL,
                        1.0f, 2.0f);
            } else {
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.BLOCK_BEACON_DEACTIVATE,
                        SoundCategory.NEUTRAL,
                        1.0f, 2.0f);
            }
        } else {
            user.getItemCooldownManager().set(T.getItem(), 14*20);
            EntityVelocityManipulation.launchEntity(user, 7, 6);
        }
        return TypedActionResult.success(T, world.isClient);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (NbtUtilities.getEnabled(stack)) {
            tooltip.add(Text.translatable("misc.deipotentia.text.enabled").formatted(Formatting.DARK_GREEN));
        }
    }

    public void deathLogic(ServerPlayerEntity player) {
        if (!player.getWorld().isClient()) {
            player.setHealth(3.0f);
            player.clearStatusEffects();

            player.getWorld().sendEntityStatus(player, (byte) 100); // 100 -> Custom Totem Byte

            player.extinguish();
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3*20));
        }
    }

    public void totemPopLogic(PlayerEntity player) {
        World world = player.getWorld();
        double impactRadius = 15.0;
        double generalPushStrength = 3.0;
        double verticalPushStrength = 0.4;

        for (LivingEntity entity : world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(impactRadius), entity -> entity != player)) {
            if (entity != player && entity.squaredDistanceTo(player) <= impactRadius * impactRadius) {
                entity.setOnFireFor(20);
                Vec3d direction = entity.getPos().subtract(player.getPos()).normalize();
                Vec3d pushDirection = direction.add(0, verticalPushStrength, 0);
                entity.setVelocity(pushDirection.multiply(generalPushStrength));
                TickScheduler.schedule(15, () -> EntityVelocityManipulation.freezeAndDropEntity(entity, 3 * 20));
            }
        }
    }

}
