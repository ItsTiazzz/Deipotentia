package net.tywrapstudios.deipotentia.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.tywrapstudios.blossombridge.api.logging.LoggingHandler;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.config.DeiConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NbtUtilities {
    private static final LoggingHandler<DeiConfig> LOGGER = new LoggingHandler<>(NbtUtilities.class.getName(), Deipotentia.CONFIG_MANAGER);

    /* VALUES */
    public static void setBooleanValue(final ItemStack stack, final String key, final boolean value) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }
        stack.getNbt().putBoolean(key, value);
    }

    public static boolean getBooleanValue(final ItemStack stack, final String key) {
        if (stack.hasNbt() && stack.getNbt().contains(key)) {
            return stack.getNbt().getBoolean(key);
        }
        return false;
    }

    public static void setIntegerValue(final ItemStack stack, final String key, final int value) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }
        stack.getNbt().putInt(key, value);
    }

    public static int getIntegerValue(final ItemStack stack, final String key) {
        if (stack.hasNbt() && stack.getNbt().contains(key)) {
            return stack.getNbt().getInt(key);
        }
        return 0;
    }

    /* ENABLED */
    public static boolean toggleEnabledForStack(ItemStack itemStack) {
        boolean current = getEnabled(itemStack);
        setEnabled(itemStack, !current);
        return current;
    }

    public static boolean toggleEnabledForStack(ItemStack itemStack, World world, PlayerEntity user) {
        boolean current = toggleEnabledForStack(itemStack);
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
        return current;
    }

    public static void setEnabled(ItemStack itemStack, boolean value) {
        setBooleanValue(itemStack, itemStack.getTranslationKey() + ".enabled", value);
    }

    public static boolean getEnabled(ItemStack itemStack) {
       return getBooleanValue(itemStack, itemStack.getTranslationKey() + ".enabled");
    }

    /* NBT IN PLAYER DATA LOGIC */
    public static boolean checkOnlinePlayersForItem(MinecraftServer server, Item target, String key) {
        LOGGER.debug("Checking online players for item.");
        for (ServerPlayerEntity targetPlayer : server.getPlayerManager().getPlayerList()) {
            for (DefaultedList<ItemStack> list : List.of(targetPlayer.getInventory().main, targetPlayer.getInventory().offHand)) {
                for (ItemStack stack : list) {
                    if (stack.isOf(target) && stack.hasNbt()) {
                        NbtCompound nbt = stack.getNbt();
                        if (nbt != null) {
                            LOGGER.debug("Found item in inventory of player: " + targetPlayer.getName().getString());
                            if (nbt.getBoolean(key)) {
                                LOGGER.debug("Tag already cleared, skipping: " + nbt);
                            } else {
                                nbt.putBoolean(key, true);
                                LOGGER.debug("Tag has been cleared: " + nbt);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkOfflinePlayersForItem(MinecraftServer server, String targetItemId, String key) {
        File playerDataDir = server.getSavePath(WorldSavePath.PLAYERDATA).toFile();
        LOGGER.debug("Player data dir at: " + playerDataDir);
        if (playerDataDir.exists()) {
            LOGGER.debug("Player data dir exists");
            File[] playerFiles = playerDataDir.listFiles((dir, name) -> name.endsWith(".dat"));
            assert playerFiles != null;
            for (File playerFile : playerFiles) {
                LOGGER.debug("Found player file at: " + playerFile);
                try {
                    String uuidString = playerFile.getName().replace(".dat", "");
                    UUID playerUuid = UUID.fromString(uuidString);
                    GameProfile profile = server.getUserCache().getByUuid(playerUuid).orElse(null);
                    if (profile == null) continue;

                    NbtCompound playerNbt;
                    try {
                        playerNbt = NbtIo.readCompressed(playerFile);
                    } catch (Exception e) {
                        LOGGER.error("Failed to read compressed player data at: " + playerFile);
                        e.printStackTrace();
                        continue;
                    }
                    LOGGER.debug("Found player nbt: " + playerNbt);
                    if (playerNbt != null) {
                        NbtList inventory = playerNbt.getList("Inventory", NbtElement.COMPOUND_TYPE);
                        boolean modified = checkNbtListAndSetTag(inventory, targetItemId, key);
                        LOGGER.debug("Modified: " + modified);

                        if (modified) {
                            NbtIo.writeCompressed(playerNbt, playerFile);
                            LOGGER.debug("Wrote back to player data file.");
                            return true;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Error processing offline player data: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean checkNbtListAndSetTag(NbtList inventory, String targetItemId, String key) {
        LOGGER.debug("Checking NbtList for items:" + inventory);
        for (int i = 0; i < inventory.size(); i++) {
            LOGGER.debug("Checking item at index: " + i);
            NbtCompound itemTag = inventory.getCompound(i);
            if (itemTag.getString("id").equals(Deipotentia.MOD_ID + ":" + targetItemId)) {
                NbtCompound tag = itemTag.getCompound("tag");
                LOGGER.debug("Fetched tag: " + tag);
                if (tag.getBoolean(key)) {
                    LOGGER.debug("Tag already cleared, skipping: " + tag);
                    return false;
                } else {
                    tag.putBoolean(key, true);
                    LOGGER.debug("Modified item at index: " + i);
                    LOGGER.debug("Modified tag: " + tag);
                    return true;
                }
            }
        }
        return false;
    }
}
