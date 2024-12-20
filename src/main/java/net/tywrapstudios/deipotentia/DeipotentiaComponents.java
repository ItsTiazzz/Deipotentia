package net.tywrapstudios.deipotentia;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;
import net.tywrapstudios.deipotentia.component.PlayerViewingComponent;

public class DeipotentiaComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerPostMortemComponent> PLAYER_DEATH_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Deipotentia.MOD_ID, "death_data"), PlayerPostMortemComponent.class);
    public static final ComponentKey<PlayerViewingComponent> PLAYER_VIEWING_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Deipotentia.MOD_ID, "viewing_data"), PlayerViewingComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DEATH_COMPONENT, player -> new PlayerPostMortemComponent(), RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerForPlayers(PLAYER_VIEWING_COMPONENT, player -> new PlayerViewingComponent(), RespawnCopyStrategy.LOSSLESS_ONLY);
        Deipotentia.LOGGING.debug("Registered Components");
    }
}
