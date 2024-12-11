package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DItemGroup {
    public static final ItemGroup DEI_GROUP;

    static {
        DEI_GROUP = Registry.register(Registries.ITEM_GROUP,
                new Identifier(Deipotentia.MOD_ID, "deipotentia"), FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.agriculture.main"))
                        .icon(() -> new ItemStack(DRegistry.DItems.VALSOULSTRANGLER))
                        .entries((displayContext, entries) -> {
                            for (ItemConvertible item : DRegistry.ITEM_CONVERTIBLES) {
                                entries.add(item);
                            }
                        }).build());
    }

    public static void register() {
    }
}
