package net.tywrapstudios.deipotentia.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.effect.StrangledEffect;

public class DEffects {
    public static final StatusEffect STRANGLED;

    static {
        STRANGLED = register("strangled", new StrangledEffect());
    }

    private static StatusEffect register(String name, StatusEffect entry) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Deipotentia.MOD_ID, name), entry);
    }

    public static void register() {
    }
}
