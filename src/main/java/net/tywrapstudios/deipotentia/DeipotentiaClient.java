package net.tywrapstudios.deipotentia;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.component.PlayerViewingComponent;
import net.tywrapstudios.deipotentia.registry.DScreenHandlers;
import net.tywrapstudios.deipotentia.screen.HephaestusForgeScreen;

@Environment(EnvType.CLIENT)
public class DeipotentiaClient implements ClientModInitializer {
    public static final ManagedShaderEffect SOUL_VIEW = ShaderEffectManager.getInstance()
            .manage(new Identifier(Deipotentia.MOD_ID, "shaders/post/soul_view.json"));
    public static boolean SOUL_VIEW_ENABLED = false;

    @Override
    public void onInitializeClient() {
        HandledScreens.register(DScreenHandlers.HEPHAESTUS_FORGE_SCREEN_HANDLER, HephaestusForgeScreen::new);

        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (SOUL_VIEW_ENABLED) {
                SOUL_VIEW.render(tickDelta);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                PlayerViewingComponent component = DeipotentiaComponents.PLAYER_VIEWING_COMPONENT.get(client.player);
                Deipotentia.LOGGING.debug("PlayerViewing: " + component.isViewing() + ", " + client.player.getEntityName());
                SOUL_VIEW_ENABLED = component.isViewing();
            }
        });
    }
}
