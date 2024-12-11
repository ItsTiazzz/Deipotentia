package net.tywrapstudios.deipotentia.enchantment;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.tywrapstudios.deipotentia.registry.DEnchantments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SoulBoundEnchantment extends Enchantment {
    public SoulBoundEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return true;
    }

    public static class Logic {
        private static final HashMap<UUID, List<ItemStack>> SOULBOUND_ITEMS = new HashMap<>();

        public static void initialize() {
            ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> collect(player));
            ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> restore(newPlayer));
        }

        private static boolean collect(PlayerEntity player) {
            List<ItemStack> soulboundItems = new ArrayList<>();
            PlayerInventory inventory = player.getInventory();

            for (int i = 0; i < inventory.main.size(); i++) {
                ItemStack stack = inventory.main.get(i);
                if (!stack.isEmpty() && EnchantmentHelper.getLevel(DEnchantments.SOULBOUND, stack) > 0) {
                    soulboundItems.add(stack.copy());
                    inventory.main.set(i, ItemStack.EMPTY);
                }
            }

            for (int i = 0; i < inventory.armor.size(); i++) {
                ItemStack stack = inventory.armor.get(i);
                if (!stack.isEmpty() && EnchantmentHelper.getLevel(DEnchantments.SOULBOUND, stack) > 0) {
                    soulboundItems.add(stack.copy());
                    inventory.armor.set(i, ItemStack.EMPTY);
                }
            }

            ItemStack offhandStack = inventory.offHand.get(0);
            if (!offhandStack.isEmpty() && EnchantmentHelper.getLevel(DEnchantments.SOULBOUND, offhandStack) > 0) {
                soulboundItems.add(offhandStack.copy());
                inventory.offHand.set(0, ItemStack.EMPTY);
            }

            SOULBOUND_ITEMS.put(player.getUuid(), soulboundItems);
            return true;
        }

        private static void restore(PlayerEntity player) {
            List<ItemStack> items = SOULBOUND_ITEMS.remove(player.getUuid());
            if (items != null) {
                for (ItemStack stack : items) {
                    if (!player.getInventory().insertStack(stack)) {
                        player.dropItem(stack, true);
                    }
                }
            }
        }
    }
}
