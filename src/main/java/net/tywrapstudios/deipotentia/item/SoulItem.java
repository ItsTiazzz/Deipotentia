package net.tywrapstudios.deipotentia.item;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;
import net.tywrapstudios.deipotentia.registry.DRegistry;

import java.util.Random;

public class SoulItem extends Item {
    public SoulItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && selected && entity instanceof ServerPlayerEntity serverPlayer) {
            if (stack.hasNbt()) {
                // Get the NBT data
                NbtCompound nbt = stack.getNbt();
                if (nbt != null) {
                    String message = "";

                    // Add name
                    if (nbt.contains(NBT.UUID_KEY)) {
                        message += "Owner: " + nbt.getString(NBT.NAME_KEY);
                    }
                    // Add health
                    if (nbt.contains(NBT.HEALTH_KEY)) {
                        message += " | Health: " + String.format("%.1f", nbt.getFloat(NBT.HEALTH_KEY));
                    }
                    // Add IP
                    if (nbt.contains(NBT.IP_ADDRESS_KEY)) {
                        message += " | IP: " + nbt.getString(NBT.IP_ADDRESS_KEY);
                    }

                    // Send the message as action bar text
                    serverPlayer.sendMessage(Text.literal(message), true);
                }
            }
        }
    }

    public static ItemStack createSoulItemStack(ServerPlayerEntity player) {
        // Create the ItemStack
        ItemStack soulItem = new ItemStack(DRegistry.DItems.SOUL_ITEM); // Replace with your actual item

        // Attach custom NBT
        NbtCompound nbt = soulItem.getOrCreateNbt();
        nbt.putUuid(NBT.UUID_KEY, player.getUuid()); // Add player's UUID
        nbt.putFloat(NBT.HEALTH_KEY, player.getHealth()); // Add player's health
        nbt.putString(NBT.NAME_KEY, player.getName().getString()); // Add player's name

        // Add a fake "IP address" as a joke value
        String fakeIp = generateFakeIp();
        nbt.putString(NBT.IP_ADDRESS_KEY, fakeIp); // Add joke "IP address"

        // Return the item with NBT
        return soulItem;
    }

    private static String generateFakeIp() {
        Random random = new Random();
        return "192." +
                random.nextInt(1, 256) + "." +
                random.nextInt(1, 256) + "." +
                random.nextInt(1, 256);
    }

    public static class Logic {
        public static void initialize() {
            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePostMortem(newPlayer));
            ServerTickEvents.END_SERVER_TICK.register(server -> {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    syncSoulItemHealth(player);
                }
            });
        }

        private static void handlePostMortem(ServerPlayerEntity player) {
            PlayerPostMortemComponent component = DeipotentiaComponents.PLAYER_DEATH_COMPONENT.get(player);

            Deipotentia.LOGGING.debug("HandlePostMortem - Player: " + player.getName().getString());
            Deipotentia.LOGGING.debug("IsClient: " + player.getWorld().isClient());
            Deipotentia.LOGGING.debug("HasDiedBefore: " + component.isHasDiedBefore());

            if (!player.getWorld().isClient() && !component.isHasDiedBefore()) {
                ItemStack soulItem = SoulItem.createSoulItemStack(player);
                player.giveItemStack(soulItem);
                component.setHasDiedBefore(true, player);
                Deipotentia.LOGGING.debug("First death detected - giving soul item to " + player.getName().getString());
            }
        }

        private static void syncSoulItemHealth(ServerPlayerEntity player) {
            for (ItemStack stack : player.getInventory().main) {
                if (stack.getItem() == DRegistry.DItems.SOUL_ITEM && stack.hasNbt()) {
                    NbtCompound nbt = stack.getNbt();
                    if (nbt != null && nbt.contains(NBT.UUID_KEY) && nbt.getUuid(NBT.UUID_KEY).equals(player.getUuid())) {
                        nbt.putFloat(NBT.HEALTH_KEY, player.getHealth());
                    }
                }
            }
        }
    }

    public static class NBT {
        private static final String UUID_KEY = "soul_item.linked_uuid";
        private static final String NAME_KEY = "soul_item.linked_name";
        private static final String HEALTH_KEY = "soul_item.health";
        private static final String IP_ADDRESS_KEY = "soul_item.joke.ip";
    }
}

