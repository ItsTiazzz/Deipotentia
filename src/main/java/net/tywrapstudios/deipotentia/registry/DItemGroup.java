package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DItemGroup {
    public static final ItemGroup DEI_GROUP;

    static {
        DEI_GROUP = FabricItemGroupBuilder.create(new Identifier(Deipotentia.MOD_ID, "deipotentia"))
                .icon(() -> new ItemStack(DRegistry.DItems.VALSOULSTRANGLER))
                .appendItems((itemStacks, itemGroup) -> {
                    for (ItemConvertible item : DRegistry.ITEM_CONVERTIBLES) {
                        itemStacks.add(new ItemStack(item));
                    }
                })
                .build();
    }

    public static void register() {
    }
}
