package net.tywrapstudios.deipotentia.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DTags;

public class EntityVelocityManipulation {
    /* LAUNCHING ENTITIES */
    public static final double GRAVITY = 0.08; // Minecraft gravity

    public static void launchEntity(Entity entity, double forwardDistance, double upwardHeight) {
        double verticalVelocity = Math.sqrt(2 * GRAVITY * upwardHeight);

        double timeInAir = (2 * verticalVelocity) / GRAVITY; // Total air time
        double horizontalVelocity = forwardDistance / timeInAir;

        Vec3d lookDirection = entity.getRotationVec(1.0F).normalize();

        Vec3d launchVelocity = new Vec3d(
                lookDirection.x * horizontalVelocity,
                verticalVelocity,
                lookDirection.z * horizontalVelocity
        );

        entity.setVelocity(launchVelocity);
        entity.velocityDirty = true;
    }

    public static void freezeAndDropEntity(LivingEntity entity, int freezeDurationTicks) {
        entity.setVelocity(0, 0, 0);
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.setNoGravity(true);

        TickScheduler.schedule(freezeDurationTicks, () -> {
            if (entity.isAlive() && entity.getWorld().isChunkLoaded(entity.getBlockPos().getX(), entity.getBlockPos().getZ())) {
                entity.setNoGravity(false);
            }
        });
    }

    /* REPULSION */

    public static final double REPULSION_RADIUS = 5.0; // Radius in blocks
    public static final double REPULSION_STRENGTH = 0.5; // Strength of the push

    public static void freezeEntityForRepulsingItem(LivingEntity entity) {
        entity.setVelocity(0, 0, 0);
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.fallDistance = entity.getSafeFallDistance();
        entity.setNoGravity(true);

        TickScheduler.schedule(20, () -> {
            if (entity.isAlive() && entity.getWorld().isChunkLoaded(entity.getBlockPos())) {
                entity.setNoGravity(false);
            }
        });
    }

    public static void spawnRepulsionParticles(ServerWorld world, PlayerEntity player, int density) {
        Vec3d center = player.getPos();
        double radius = REPULSION_RADIUS;

        for (int i = 0; i < density; i++) {
            double theta = Math.random() * 2 * Math.PI;
            double phi = Math.acos(2 * Math.random() - 1);
            double x = center.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.y + radius * Math.sin(phi) * Math.sin(theta);
            double z = center.z + radius * Math.cos(phi);

            world.spawnParticles(ParticleTypes.SCRAPE, x, y, z, 1, 0, 0, 0, 0); // Adjust particle type and count
        }
    }

    public static boolean isHoldingRepulsionItem(PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        boolean isMainValid = NbtUtilities.getEnabled(mainHandStack) && mainHandStack.isIn(DTags.Items.REPULSING.get());
        boolean isOffHandValid = NbtUtilities.getEnabled(offHandStack) && offHandStack.isIn(DTags.Items.REPULSING.get());
        return isMainValid || isOffHandValid;
    }

    public static void pushEntityAwayFromPlayer(PlayerEntity player, Entity entity) {
        Vec3d playerPos = player.getPos();
        Vec3d entityPos = entity.getPos();

        Vec3d direction = entityPos.subtract(playerPos).normalize();

        Vec3d repulsion = direction.multiply(REPULSION_STRENGTH);
        entity.addVelocity(repulsion.x, repulsion.y * 0.5, repulsion.z);
        entity.velocityDirty = true;
    }
}
