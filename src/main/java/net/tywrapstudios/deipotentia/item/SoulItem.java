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
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import net.tywrapstudios.deipotentia.util.TickScheduler;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SoulItem extends Item {
    public SoulItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && selected && entity instanceof ServerPlayerEntity viewer) {
            if (stack.hasNbt()) {
                NbtCompound nbt = stack.getNbt();
                if (nbt != null && nbt.contains(NBT.UUID)) {
                    UUID linkedPlayerUuid = nbt.getUuid(NBT.UUID);
                    ServerPlayerEntity linkedPlayer = viewer.getServer().getPlayerManager().getPlayer(linkedPlayerUuid);

                    if (linkedPlayer != null) {
                        nbt.putFloat(NBT.HEALTH, linkedPlayer.getHealth());
                        nbt.putString(NBT.POSITION, linkedPlayer.getPos().toString().replace("(", "").replace(")", ""));
                    }

                    Text text = Text.empty()
                            .append("Vessel Name: ")
                            .append(Text.literal(nbt.contains(NBT.NAME) ? nbt.getString(NBT.NAME) : "Could not fetch!")
                                    .formatted(Formatting.GOLD))
                            .append(" | Health: ")
                            .append(Text.literal(nbt.contains(NBT.HEALTH) ? String.format("%.1f", nbt.getFloat(NBT.HEALTH)) : "Could not fetch!")
                                    .formatted(getColourFromHealth(nbt.getFloat(NBT.HEALTH))))
                            .append(" | Pos: ")
                            .append(getPosText(nbt.contains(NBT.POSITION) ? nbt.getString(NBT.POSITION) : "N/A"))
                            .append(Text.literal(Deipotentia.CONFIG_MANAGER.getConfig().joke_mode ?
                                    " | IP: " + nbt.getString(NBT.IP_ADDRESS) : ""));

                    if (nbt.getBoolean(NBT.CLEANED)) {
                        text = Text.empty()
                                .append("Vessel Name: ")
                                .append(Text.literal(nbt.contains(NBT.NAME) ? nbt.getString(NBT.NAME) : "Could not fetch!")
                                        .formatted(Formatting.OBFUSCATED, Formatting.GOLD))
                                .append(" | Health: ")
                                .append(Text.literal("20.0")
                                        .formatted(Formatting.OBFUSCATED, Formatting.DARK_GREEN))
                                .append(" | Pos: ")
                                .append(Text.literal("12, 85, 20")
                                        .formatted(Formatting.OBFUSCATED, Formatting.DARK_AQUA))
                                .append(Deipotentia.CONFIG_MANAGER.getConfig().joke_mode ? " | IP: " : "")
                                .append(Deipotentia.CONFIG_MANAGER.getConfig().joke_mode ? Text.literal("Blocked by CharterVPN") : Text.literal(""));
                    }

                    viewer.sendMessage(text, true);
                }
            }
        }
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    private static Text getPosText(String posString) {
        if (posString.contains("N/A")) {
            return Text.literal("N/A, N/A, N/A").formatted(Formatting.DARK_RED);
        } else {
            List<String> positions = List.of(posString.split(", "));
            return Text.empty()
                    .append(Text.literal(String.format("%.0f, ", Float.valueOf(positions.get(0))))
                            .formatted(Formatting.DARK_AQUA))
                    .append(Text.literal(String.format("%.0f", Float.valueOf(positions.get(1))))
                            .formatted(Formatting.DARK_AQUA))
                    .append(Text.literal(String.format(", %.0f", Float.valueOf(positions.get(2))))
                            .formatted(Formatting.DARK_AQUA));
        }
    }

    private static Formatting getColourFromHealth(float health) {
        if (health >= 17) {
            return Formatting.DARK_GREEN;
        } else if (health >= 6) {
            return Formatting.GOLD;
        } else if (health > 0) {
            return Formatting.DARK_RED;
        } else {
            return Formatting.BLACK;
        }
    }

    public static ItemStack createSoulItemStack(ServerPlayerEntity player) {
        ItemStack soulItem = new ItemStack(DRegistry.DItems.SOUL_ITEM); // Replace with your actual item

        NbtCompound nbt = soulItem.getOrCreateNbt();
        nbt.putUuid(NBT.UUID, player.getUuid());
        nbt.putFloat(NBT.HEALTH, player.getHealth());
        nbt.putString(NBT.NAME, player.getName().getString());
        nbt.putString(NBT.POSITION, player.getPos().toString().replace("(", "").replace(")",""));
        nbt.putString(NBT.IP_ADDRESS, generateFakeIp());
        nbt.putBoolean(NBT.CLEANED, false);

        return soulItem;
    }

    private static String generateFakeIp() {
        Random random = new Random();
        return "192." +
                random.nextInt(1, 256) + "." +
                random.nextInt(1, 256) + "." +
                random.nextInt(1, 256);
    }

    public static class NBT {
        private static final String UUID = "LinkedUUID";
        private static final String NAME = "LinkedName";
        private static final String HEALTH = "LinkedHealth";
        private static final String IP_ADDRESS = "LinkedIP";
        private static final String POSITION = "LinkedPosition";
        protected static final String CLEANED = "IsClean";
    }

    public static class Logic {
        public static void initialize() {
            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePostMortem(newPlayer));
            ServerTickEvents.END_SERVER_TICK.register(server -> {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    syncSoulItem(player);
                }
            });
        }

        private static void handlePostMortem(ServerPlayerEntity player) {
            TickScheduler.schedule(10, () -> player.setNoGravity(false));
            PlayerPostMortemComponent component = DeipotentiaComponents.PLAYER_DEATH_COMPONENT.get(player);

            Deipotentia.LOGGING.debug("HandlePostMortem - Player: " + player.getName().getString());
            Deipotentia.LOGGING.debug("IsClient: " + player.getWorld().isClient());
            Deipotentia.LOGGING.debug("HasDiedBefore: " + component.hasDiedBefore());

            if (!player.getWorld().isClient() && !component.hasDiedBefore()) {
                ItemStack soulItem = SoulItem.createSoulItemStack(player);
                player.giveItemStack(soulItem);
                player.sendMessage(Text.literal(String.format("""
                        Hey, %s, it seems this is the first time you've died.
                        You just received a Soul Manifestation Item,
                        it displays some statistics of whoever it's linked to.
                        This is updated in real time, so make sure not to lose it
                        if you value your privacy!
                        
                        Lost your manifestation anyways? Just bleach your soul using
                        Soul Bleacher and it will obfuscate the text for anyone to read.
                        This will also give you the ability to die again and get a new item.""", player.getName().getString()))
                        .formatted(Formatting.GRAY));
                component.setHasDiedBefore(true, player);
                Deipotentia.LOGGING.debug("Valid death detected - giving soul item to " + player.getName().getString());
            }
        }

        private static void syncSoulItem(ServerPlayerEntity player) {
            float currentHealth = player.getHealth();
            String currentPos = player.getPos().toString().replace("(", "").replace(")", "");

            for (DefaultedList<ItemStack> list : List.of(player.getInventory().main, player.getInventory().offHand)) {
                for (ItemStack stack : list) {
                    if (stack.getItem() == DRegistry.DItems.SOUL_ITEM && stack.hasNbt()) {
                        NbtCompound nbt = stack.getNbt();
                        if (nbt != null && nbt.contains(NBT.UUID) &&
                                nbt.getUuid(NBT.UUID).equals(player.getUuid()) &&
                                !nbt.getBoolean(NBT.CLEANED)) {
                            nbt.putFloat(NBT.HEALTH, currentHealth);
                            nbt.putString(NBT.POSITION, currentPos);
                        }
                    }
                }
            }
        }
    }
}

