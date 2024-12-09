package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;

public class DItemGroup {
    public static ItemGroup DEI_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Deipotentia.MOD_ID, "deipotentia"), () -> new ItemStack(DRegistry.DItems.VALSOULSTRANGLER)
    );
    public static void register() {
    }
}
