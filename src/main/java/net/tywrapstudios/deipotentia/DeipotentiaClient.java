package net.tywrapstudios.deipotentia;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.tywrapstudios.deipotentia.registry.DScreenHandlers;
import net.tywrapstudios.deipotentia.screen.HephaestusForgeScreen;

public class DeipotentiaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(DScreenHandlers.HEPHAESTUS_FORGE_SCREEN_HANDLER, HephaestusForgeScreen::new);
    }
}
