package net.tywrapstudios.deipotentia.item;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tywrapstudios.deipotentia.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;

public class SoulItem extends Item {
    public SoulItem(Settings settings) {
        super(settings);
    }

    public static class Logic {
        public static void initialize() {
            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> handlePostMortem(newPlayer));
        }

        private static void handlePostMortem(ServerPlayerEntity player) {
            PlayerPostMortemComponent component = DeipotentiaComponents.PLAYER_DEATH_COMPONENT.get(player);

            if (!component.isHasDiedBefore()) {
                component.setHasDiedBefore(true);
            }
        }
    }

    public static class NBT {
        
    }
}

