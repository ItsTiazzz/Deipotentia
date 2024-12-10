package net.tywrapstudios.deipotentia;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.component.PlayerPostMortemComponent;

public class DeipotentiaComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerPostMortemComponent> PLAYER_DEATH_COMPONENT =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier(Deipotentia.MOD_ID, "death_data"), PlayerPostMortemComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DEATH_COMPONENT, player -> new PlayerPostMortemComponent(),
                RespawnCopyStrategy.ALWAYS_COPY);
        Deipotentia.LOGGING.debug("Registered PlayerPostMortemComponent");
    }
}
