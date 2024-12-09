package net.tywrapstudios.deipotentia.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Deactivated {
    public static class ValSoulStrangler extends Item{
        public ValSoulStrangler(Settings settings) {
            super(settings);
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            String val = Deipotentia.CONFIG_MANAGER.getConfig().valUuid;
            if (!Deipotentia.CONFIG_MANAGER.getConfig().val) {
                Deipotentia.CONFIG_MANAGER.getConfig().val = true;
                Deipotentia.CONFIG_MANAGER.saveConfig();
                return getActivationResult(world, user, hand, val, DRegistry.DItems.VALSOULSTRANGLER);
            } else {
                world.playSound(null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_CONDUIT_DEACTIVATE,
                        SoundCategory.MASTER,
                        5.0f, 0.5f);
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
        }
    }

    public static class AngelsGuard extends Item {
        public AngelsGuard(Settings settings) {
            super(settings);
        }

        @Override
        public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
            String val = Deipotentia.CONFIG_MANAGER.getConfig().stenUuid;
            return getActivationResult(world, user, hand, val, DRegistry.DItems.ANGELS_GUARD);
        }
    }

    private static @NotNull TypedActionResult<ItemStack> getActivationResult(World world, PlayerEntity user, Hand hand, String val, ItemConvertible newItem) {
        String god = Deipotentia.CONFIG_MANAGER.getConfig().godUuid;
        String player = user.getUuid().toString();
        Deipotentia.LOGGING.debug(val + "|" + player + "|" + god);
        if (Objects.equals(val, player) || Objects.equals(god, player)) {
            world.playSound(user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.BLOCK_CONDUIT_ACTIVATE,
                    SoundCategory.MASTER,
                    5.0f, 0.5f, false);
            user.setStackInHand(hand, new ItemStack(newItem));
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        } else {
            user.sendMessage(Text.translatable("misc.deipotentia.text.invalid_uuid_" + world.random.nextBetween(0, 3)).formatted(Formatting.DARK_GRAY));
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
}
