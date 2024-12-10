package net.tywrapstudios.deipotentia.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.util.EntityVelocityManipulation;
import net.tywrapstudios.deipotentia.util.NbtUtilities;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.tywrapstudios.deipotentia.util.EntityVelocityManipulation.REPULSION_RADIUS;

public class AngelsGuardItem extends Item {
    public AngelsGuardItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity user, int slot, boolean selected) {
        if (user instanceof ServerPlayerEntity player) {
            if (EntityVelocityManipulation.isHoldingRepulsionItem(player)) {
                // Get all entities within the radius
                Box boundingBox = user.getBoundingBox().expand(REPULSION_RADIUS);
                for (Entity entity : world.getOtherEntities(player, boundingBox)) {
                    // Push the entity away
                    EntityVelocityManipulation.pushEntityAwayFromPlayer(player, entity);
                    Deipotentia.LOGGING.debug("Found entity in radius: " + entity.getClass().getName());
                }
                // Spawn particles around the player to indicate the range
                EntityVelocityManipulation.spawnRepulsionParticles(player.getWorld(), player, Deipotentia.CONFIG_MANAGER.getConfig().particle_density);
                // Freeze the player as compensation
                EntityVelocityManipulation.freezeEntityForRepulsingItem(player);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack T = user.getStackInHand(hand);
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
        user.setNoGravity(false);

        return TypedActionResult.success(T, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (NbtUtilities.getEnabled(stack)) {
            tooltip.add(Text.translatable("misc.deipotentia.text.enabled").formatted(Formatting.DARK_GREEN));
        }
    }
}
