package net.tywrapstudios.deipotentia.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.registry.DTags;

public class EntityVelocityManipulation {
    public static final double GRAVITY = 0.08; // Minecraft gravity

    public static void launchEntity(Entity entity, double forwardDistance, double upwardHeight) {
        // Calculate vertical velocity for the desired height
        double verticalVelocity = Math.sqrt(2 * GRAVITY * upwardHeight);

        // Calculate horizontal velocity for the desired distance
        double timeInAir = (2 * verticalVelocity) / GRAVITY; // Total air time
        double horizontalVelocity = forwardDistance / timeInAir;

        // Get the player's look direction
        Vec3d lookDirection = entity.getRotationVec(1.0F).normalize();

        // Apply the calculated velocities to the look direction
        Vec3d launchVelocity = new Vec3d(
                lookDirection.x * horizontalVelocity,
                verticalVelocity,
                lookDirection.z * horizontalVelocity
        );

        // Set the player's velocity
        entity.setVelocity(launchVelocity);
        entity.velocityDirty = true;
    }

    public static void freezeAndDropEntity(LivingEntity entity, int freezeDurationTicks) {
        if (!entity.isAlive() || !entity.world.isChunkLoaded(entity.getBlockPos())) {
            return;
        }

        // Disable motion and gravity
        entity.setVelocity(0, 0, 0);
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.setNoGravity(true);

        // Schedule re-enabling gravity
        TickScheduler.schedule(freezeDurationTicks, () -> {
            if (entity.isAlive() && entity.world.isChunkLoaded(entity.getBlockPos())) {
                entity.setNoGravity(false);
            }
        });
    }

    public static void freezeEntityForRepulsingItem(LivingEntity entity) {
        if (!entity.isAlive() || !entity.world.isChunkLoaded(entity.getBlockPos())) {
            return;
        }

        // Disable motion and gravity
        entity.setVelocity(0, 0, 0);
        entity.setMovementSpeed(-1);
        entity.velocityDirty = true;
        entity.velocityModified = true;
        entity.fallDistance = 0;
        entity.setNoGravity(true);

        // Schedule re-enabling gravity
        TickScheduler.schedule(20, () -> {
            if (entity.isAlive() && entity.world.isChunkLoaded(entity.getBlockPos())) {
                entity.setNoGravity(false);
            }
        });
    }

    public static final double REPULSION_RADIUS = 5.0; // Radius in blocks
    public static final double REPULSION_STRENGTH = 0.5; // Strength of the push

    public static void initialize() {
//        ServerTickEvents.END_WORLD_TICK.register(EntityVelocityManipulation::onWorldTick);
    }

//    private static void onWorldTick(ServerWorld world) {
//        for (PlayerEntity player : world.getPlayers()) {
//            // Check if the player is holding the specific item
//            if (isHoldingRepulsionItem(player)) {
//                // Get all entities within the radius
//                Box boundingBox = player.getBoundingBox().expand(REPULSION_RADIUS);
//                for (Entity entity : world.getEntitiesByClass(Entity.class, boundingBox, e -> e != player)) {
//                    // Push the entity away
//                    pushEntityAwayFromPlayer(player, entity);
//                }
//                // Spawn particles around the player to indicate the range
//                spawnRepulsionParticles(world, player, Deipotentia.CONFIG_MANAGER.getConfig().particle_density);
//                // Freeze the player as compensation
//                freezeEntityForRepulsingItem(player);
//            }
//        }
//    }

    public static void spawnRepulsionParticles(ServerWorld world, PlayerEntity player, int density) {
        Vec3d center = player.getPos();
        double radius = REPULSION_RADIUS;

        // Generate particles in a spherical pattern
        for (int i = 0; i < density; i++) { // Adjust for density
            double theta = Math.random() * 2 * Math.PI; // Angle around the vertical axis
            double phi = Math.acos(2 * Math.random() - 1); // Angle from the vertical axis
            double x = center.x + radius * Math.sin(phi) * Math.cos(theta);
            double y = center.y + radius * Math.sin(phi) * Math.sin(theta);
            double z = center.z + radius * Math.cos(phi);

            // Spawn a particle at the calculated position
            world.spawnParticles(ParticleTypes.SCRAPE, x, y, z, 1, 0, 0, 0, 0); // Adjust particle type and count
        }
    }

    public static boolean isHoldingRepulsionItem(PlayerEntity player) {
        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        return (NBTUtilities.getEnabled(mainHandStack) || NBTUtilities.getEnabled(offHandStack)) && (mainHandStack.streamTags().anyMatch(itemTagKey -> itemTagKey.isOf(DTags.Items.REPULSING.get().registry())) || offHandStack.streamTags().anyMatch(itemTagKey -> itemTagKey.isOf(DTags.Items.REPULSING.get().registry())));
    }

    public static void pushEntityAwayFromPlayer(PlayerEntity player, Entity entity) {
        Vec3d playerPos = player.getPos();
        Vec3d entityPos = entity.getPos();

        // Calculate the direction vector from the player to the entity
        Vec3d direction = entityPos.subtract(playerPos).normalize();

        // Apply a velocity to push the entity away
        Vec3d repulsion = direction.multiply(REPULSION_STRENGTH);
        entity.addVelocity(repulsion.x, repulsion.y * 0.5, repulsion.z); // Slight vertical push
        entity.velocityDirty = true;
    }
}
