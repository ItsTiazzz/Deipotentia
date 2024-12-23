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
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.component.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;
import net.tywrapstudios.deipotentia.component.PlayerViewingComponent;
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null && nbt.contains(NBT.UUID)) {
                UUID linkedPlayerUuid = nbt.getUuid(NBT.UUID);
                ServerPlayerEntity linkedPlayer = user.getServer().getPlayerManager().getPlayer(linkedPlayerUuid);
                PlayerViewingComponent component = DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.get(user);
                int ticks = 10*20;
                boolean self = !Deipotentia.CONFIG_MANAGER.getConfig().better_viewing && user == linkedPlayer;

                if (self) {
                    ticks = 3*20;
                }

                if (linkedPlayer != null) {
                    component.setViewingData(true, linkedPlayerUuid, ticks, self, user);
                    user.getItemCooldownManager().set(this, 15 * 20);
                }
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    public static void tickViewing(ServerPlayerEntity viewer) {
        PlayerViewingComponent component = DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.get(viewer);
        ServerPlayerEntity target = viewer.getServer().getPlayerManager().getPlayer(component.getViewingTarget());

        if (component.getTime() <= -1) {
            return;
        }

        if (component.getTime() > 0) {
            if (target != null) {
                viewer.setCameraEntity(target);
                viewer.changeGameMode(GameMode.SPECTATOR);
                component.setTime(component.getTime() -1, viewer);
                viewer.sendMessage(Text.literal("Observing " + target.getName().getString() + " " + component.getTime() / 20).formatted(Formatting.YELLOW), true);
            }
        } else {
            viewer.setCameraEntity(viewer);
            viewer.changeGameMode(component.getGameMode());
            double[] pos = component.getPosition();
            Deipotentia.LOGGING.debug(String.format("[TickViewing] Setting position to %s, %s, %s", pos[0], pos[1], pos[2]));
            viewer.teleport(pos[0], pos[1], pos[2]);
            component.setTime(-1, viewer);
            component.setViewing(false, viewer);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient && (slot <= 8 || selected) && entity instanceof ServerPlayerEntity viewer) {
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
                                .append(Text.literal("00, 00, 00")
                                        .formatted(Formatting.OBFUSCATED, Formatting.DARK_AQUA))
                                .append(Deipotentia.CONFIG_MANAGER.getConfig().joke_mode ? " | IP: " : "")
                                .append(Deipotentia.CONFIG_MANAGER.getConfig().joke_mode ? Text.literal("Blocked by CharterVPN") : Text.literal(""));
                    }
                    PlayerViewingComponent component = DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.get(viewer);
                    if (!component.isViewing()) {
                        viewer.sendMessage(text, true);
                    }
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
        protected static final String UUID = "LinkedUUID";
        protected static final String NAME = "LinkedName";
        protected static final String HEALTH = "LinkedHealth";
        protected static final String IP_ADDRESS = "LinkedIP";
        protected static final String POSITION = "LinkedPosition";
        protected static final String CLEANED = "IsClean";
    }

    public static class Logic {
        public static void initialize() {
            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePostMortem(newPlayer));
            ServerTickEvents.END_SERVER_TICK.register(server -> {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    syncSoulItem(player);
                    tickViewing(player);
                }
            });
        }

        private static void handlePostMortem(ServerPlayerEntity player) {
            TickScheduler.schedule(10, () -> player.setNoGravity(false));
            PlayerPostMortemComponent component = DeipotentiaComponents.PLAYER_DEATH_COMPONENT.get(player);

            Deipotentia.LOGGING.debug("HandlePostMortem - Player: " + player.getName().getString());
            Deipotentia.LOGGING.debug("IsClient: " + player.getWorld().isClient());
            Deipotentia.LOGGING.debug("HasDiedBefore: " + component.hasDiedBefore());

            if (player.getRandom().nextBoolean()) {
                player.giveItemStack(new ItemStack(DRegistry.DItems.EMPTY_SOUL));
            }

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

