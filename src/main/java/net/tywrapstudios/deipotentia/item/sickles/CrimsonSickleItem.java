package net.tywrapstudios.deipotentia.item.sickles;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrimsonSickleItem extends HoeItem {
    public CrimsonSickleItem(Settings settings) {
        super(ToolMaterials.DIAMOND, 3, -2f, settings.rarity(Rarity.UNCOMMON));
    }

    @Override
    public Text getName() {
        return Text.translatable(this.getTranslationKey()).formatted(Formatting.DARK_RED);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ITEM_FIRECHARGE_USE,
                SoundCategory.NEUTRAL,
                0.7F,
                0.1F / (world.random.nextFloat() * 0.4F + 0.8F)
        );

        if (!world.isClient()) {
            SmallFireballEntity E = new SmallFireballEntity(world, user, 0d, -0.2d, 0d);
            E.setVelocity(user, user.getPitch(), user.getYaw(), user.getRoll()-1, 2f, 0.0f);
            world.spawnEntity(E);
        }

        user.getItemCooldownManager().set(this,10);
        ItemStack I = user.getStackInHand(hand);
        I.damage(1,user,playerEntity -> playerEntity.sendToolBreakStatus(hand));

        return TypedActionResult.success(I, world.isClient());
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        user.getStackInHand(hand).damage(1,user,playerEntity -> playerEntity.sendToolBreakStatus(hand));
        entity.setOnFireFor(4);
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.crimson_sickle").formatted(Formatting.ITALIC, Formatting.GOLD));
        tooltip.add(Text.translatable("tooltip.deipotentia.sickle.crimson_sickle.sec").formatted(Formatting.ITALIC, Formatting.DARK_GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
