package net.tywrapstudios.deipotentia.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DSounds {
    public static final SoundEvent BLAHAJ_HONK;
    public static final SoundEvent WWDITD;

    static {
        BLAHAJ_HONK = registerSoundEvent("blahaj_honk");
        WWDITD = registerSoundEvent("what_we_did_in_the_desert");
    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Deipotentia.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}
}
