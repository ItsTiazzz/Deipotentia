package net.tywrapstudios.deipotentia.item.sickles;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WarpedSickleItem extends HoeItem {
    public WarpedSickleItem(Settings settings) {
        super(ToolMaterials.DIAMOND, 3, -2f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        stack.removeCustomName();
        stack.setCustomName(Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(
                Formatting.DARK_AQUA).withItalic(false)));
        return stack.getName();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack H = user.getStackInHand(hand);
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ENTITY_ENDER_EYE_DEATH,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (!world.isClient()) {
            EnderPearlEntity E = new EnderPearlEntity(world, user);
            E.setItem(H);
            E.setVelocity(user, user.getPitch(), user.getYaw(), user.getRoll(), 1.5f, 0.0f);
            world.spawnEntity(E);
        }
        H.damage(1, user, playerEntity -> playerEntity.sendToolBreakStatus(hand));
        user.getItemCooldownManager().set(this, 10);
        return TypedActionResult.success(H, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.deipotentia.sickle.warped_sickle").formatted(Formatting.ITALIC, Formatting.GOLD));
            tooltip.add(Text.translatable("tooltip.deipotentia.sickle.warped_sickle.sec").formatted(Formatting.ITALIC, Formatting.DARK_GRAY));
        } else {
            Text shift = Text.literal("[").formatted(Formatting.GOLD)
                    .append(Text.translatable("tooltip.deipotentia.misc.hold_shift").formatted(Formatting.GRAY, Formatting.ITALIC))
                    .append(Text.literal("]").formatted(Formatting.GOLD));
            tooltip.add(shift);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}

