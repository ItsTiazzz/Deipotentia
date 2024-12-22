package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.screen.HephaestusForgeScreenHandler;

public class DScreenHandlers {
    public static ScreenHandlerType<HephaestusForgeScreenHandler> HEPHAESTUS_FORGE_SCREEN_HANDLER;

    static {
        HEPHAESTUS_FORGE_SCREEN_HANDLER = create("hephaestus_forge", new ExtendedScreenHandlerType<>((i, inv, buf) -> new HephaestusForgeScreenHandler(i, inv)));
    }

    public static void register() {}

    private static <T extends ScreenHandlerType<?>> T create(String name, T type) {
        return Registry.register(Registries.SCREEN_HANDLER, Deipotentia.id(name), type);
    }
}
