package net.tywrapstudios.deipotentia;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.tywrapstudios.deipotentia.component.DeipotentiaComponents;
import net.tywrapstudios.deipotentia.component.PlayerViewingComponent;
import net.tywrapstudios.deipotentia.registry.DScreenHandlers;
import net.tywrapstudios.deipotentia.screen.HephaestusForgeScreen;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class DeipotentiaClient implements ClientModInitializer {
    public static final ManagedShaderEffect SOUL_VIEW = ShaderEffectManager.getInstance()
            .manage(Deipotentia.id("shaders/post/soul_view.json"));
    public static final ManagedShaderEffect SOUL_VIEW_DARK = ShaderEffectManager.getInstance()
            .manage(Deipotentia.id("shaders/post/soul_view_dark.json"));
    public static boolean SOUL_VIEW_ENABLED = false;
    public static boolean DARK = false;

    @Override
    public void onInitializeClient() {
        HandledScreens.register(DScreenHandlers.HEPHAESTUS_FORGE_SCREEN_HANDLER, HephaestusForgeScreen::new);

        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (SOUL_VIEW_ENABLED) {
                if (DARK) {
                    SOUL_VIEW_DARK.render(tickDelta);
                } else {
                    SOUL_VIEW.render(tickDelta);
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                PlayerViewingComponent component = DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.get(client.player);
                SOUL_VIEW_ENABLED = component.isViewing();
                DARK = component.isSelf();
            }
        });

        FabricLoader.getInstance().getModContainer(Deipotentia.MOD_ID).ifPresent(modContainer ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Deipotentia.id("legacy_textures"),
                        modContainer,
                        ResourcePackActivationType.NORMAL
                ));
    }
}
