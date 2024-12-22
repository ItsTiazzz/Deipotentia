package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DItemGroup {
    public static final ItemGroup DEI_GROUP;

    static {
        DEI_GROUP = Registry.register(Registries.ITEM_GROUP,
                Deipotentia.id("deipotentia"), FabricItemGroup.builder()
                        .displayName(Text.translatable("itemgroup.deipotentia"))
                        .icon(() -> new ItemStack(DRegistry.DItems.VALSOULSTRANGLER))
                        .entries((displayContext, entries) -> {
                            for (ItemConvertible item : DRegistry.ITEM_CONVERTIBLES) {
                                entries.add(item);
                            }
                        }).build());
    }

    public static void register() {}
}
