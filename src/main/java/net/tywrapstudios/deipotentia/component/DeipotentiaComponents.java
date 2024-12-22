package net.tywrapstudios.deipotentia.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DeipotentiaComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerPostMortemComponent> PLAYER_DEATH_COMPONENT = ComponentRegistryV3.INSTANCE
            .getOrCreate(Deipotentia.id("death_data"), PlayerPostMortemComponent.class);
    public static final ComponentKey<PlayerViewingComponent> PLAYER_VIEWING_COMPONENT = ComponentRegistryV3.INSTANCE
            .getOrCreate(Deipotentia.id("viewing_data"), PlayerViewingComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DEATH_COMPONENT, player -> new PlayerPostMortemComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(PLAYER_VIEWING_COMPONENT, player -> new PlayerViewingComponent(), RespawnCopyStrategy.LOSSLESS_ONLY);
        Deipotentia.LOGGING.debug("Registered Components");
    }
}
