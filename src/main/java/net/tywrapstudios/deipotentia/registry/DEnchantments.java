package net.tywrapstudios.deipotentia.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.enchantment.SoulBoundEnchantment;

public class DEnchantments {
    public static final Enchantment SOULBOUND;

    static {
        SOULBOUND = create("soulbound", new SoulBoundEnchantment());
    }

    private static Enchantment create(String id, Enchantment entry) {
        return Registry.register(Registry.ENCHANTMENT, new Identifier(Deipotentia.MOD_ID, id), entry);
    }

    public static void register() {
    }
}
