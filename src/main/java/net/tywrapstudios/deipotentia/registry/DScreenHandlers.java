package net.tywrapstudios.deipotentia.registry;

import net.minecraft.screen.ScreenHandlerType;
import net.tywrapstudios.deipotentia.screen.HephaestusForgeScreenHandler;

public class DScreenHandlers {
    public static ScreenHandlerType<HephaestusForgeScreenHandler> HEPHAESTUS_FORGE_SCREEN_HANDLER;

    public static void register() {
        HEPHAESTUS_FORGE_SCREEN_HANDLER = new ScreenHandlerType<>(HephaestusForgeScreenHandler::new);
    }
}
