package net.tywrapstudios.deipotentia.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.util.NBTUtilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AngelsGuardItem extends Item {
    public AngelsGuardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack T = user.getStackInHand(hand);
        boolean current = NBTUtilities.toggleEnabledForStack(T);
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
        user.setNoGravity(false);

        return TypedActionResult.success(T, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (NBTUtilities.getEnabled(stack)) {
            tooltip.add(Text.translatable("misc.deipotentia.text.enabled").formatted(Formatting.DARK_GREEN));
        }
    }
}
