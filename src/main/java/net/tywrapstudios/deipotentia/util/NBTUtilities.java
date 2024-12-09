package net.tywrapstudios.deipotentia.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class NBTUtilities {

    /* VALUES */
    public static void setBooleanValue(final ItemStack stack, final String key, final boolean value) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }
        stack.getNbt().putBoolean(key, value);
    }

    public static boolean getBooleanValue(final ItemStack stack, final String key) {
        if (stack.hasNbt() && stack.getNbt().contains(key)) {
            return stack.getNbt().getBoolean(key);
        }
        return false;
    }

    public static void setIntegerValue(final ItemStack stack, final String key, final int value) {
        if (!stack.hasNbt()) {
            stack.setNbt(new NbtCompound());
        }
        stack.getNbt().putInt(key, value);
    }

    public static int getIntegerValue(final ItemStack stack, final String key) {
        if (stack.hasNbt() && stack.getNbt().contains(key)) {
            return stack.getNbt().getInt(key);
        }
        return 0;
    }

    /* ENABLED */
    public static boolean toggleEnabledForStack(ItemStack itemStack) {
        boolean current = getEnabled(itemStack);
        setEnabled(itemStack, !current);
        return current;
    }

    public static void setEnabled(ItemStack itemStack, boolean value) {
        setBooleanValue(itemStack, "enabled", value);
    }

    public static boolean getEnabled(ItemStack itemStack) {
       return getBooleanValue(itemStack, "enabled");
    }
}
