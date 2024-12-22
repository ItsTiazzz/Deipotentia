package net.tywrapstudios.deipotentia.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.enchantment.SoulBoundEnchantment;

public class DEnchantments {
    public static final Enchantment SOULBOUND;

    static {
        SOULBOUND = create("soulbound", new SoulBoundEnchantment());
    }

    private static Enchantment create(String id, Enchantment entry) {
        return Registry.register(Registries.ENCHANTMENT, Deipotentia.id(id), entry);
    }

    public static void register() {}
}
