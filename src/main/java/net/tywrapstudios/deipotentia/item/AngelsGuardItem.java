package net.tywrapstudios.deipotentia.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
                Box boundingBox = user.getBoundingBox().expand(REPULSION_RADIUS);
                for (Entity entity : world.getNonSpectatingEntities(Entity.class, boundingBox)) {
                    EntityVelocityManipulation.pushEntityAwayFromPlayer(player, entity);
                    Deipotentia.LOGGING.debug("Found entity in radius of class: " + entity.getClass().getName());
                }
                EntityVelocityManipulation.spawnRepulsionParticles(player.getServerWorld(), player, Deipotentia.CONFIG_MANAGER.getConfig().particle_density);
                EntityVelocityManipulation.freezeEntityForRepulsingItem(player);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack T = user.getStackInHand(hand);
        NbtUtilities.toggleEnabledForStack(T, world, user);
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
