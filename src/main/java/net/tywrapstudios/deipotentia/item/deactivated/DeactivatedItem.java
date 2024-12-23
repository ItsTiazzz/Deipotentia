package net.tywrapstudios.deipotentia.item.deactivated;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;

import java.util.Objects;

public abstract class DeactivatedItem extends Item implements Deactivated {
    public DeactivatedItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public Text getName(ItemStack stack) {
        stack.removeCustomName();
        stack.setCustomName(Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(
                Formatting.DARK_GRAY).withItalic(false)));
        return stack.getName();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (getActivatingStatus()) {
            return activate(world, user, hand, getActivatorUuid(), getActivatedItem());
        } else {
            world.playSound(null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.BLOCK_CONDUIT_DEACTIVATE,
                        SoundCategory.MASTER,
                        5.0f, 0.5f);
            user.sendMessage(Text.translatable("misc.deipotentia.text.invalid_usage"));
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }

    private TypedActionResult<ItemStack> activate(World world, PlayerEntity user, Hand hand, String activatorUuid, ItemConvertible newItem) {
        String god = Deipotentia.CONFIG_MANAGER.getConfig().godUuid;
        String current = user.getUuid().toString();
        Deipotentia.LOGGING.debug(activatorUuid + "|" + current + "|" + god);

        if (Objects.equals(activatorUuid, current) || Objects.equals(god, current)) {
            handleBeforeActivation();
            world.playSound(null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.BLOCK_CONDUIT_ACTIVATE,
                    SoundCategory.MASTER,
                    5.0f, 0.5f);
            user.setStackInHand(hand, new ItemStack(newItem));
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        } else {
            user.sendMessage(Text.translatable("misc.deipotentia.text.invalid_uuid_" + world.random.nextBetween(0, 3)).formatted(Formatting.DARK_GRAY));
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
}
