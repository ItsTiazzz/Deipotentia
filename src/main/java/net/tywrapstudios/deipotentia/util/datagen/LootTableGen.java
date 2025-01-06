package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import static net.tywrapstudios.deipotentia.registry.DRegistry.*;

public class LootTableGen extends FabricBlockLootTableProvider {
    protected LootTableGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(DBlocks.BLAHAJ_BLOCK);
        addDrop(DBlocks.CLOTH);
        addDrop(DBlocks.CLOTH_CARPET);
        addDrop(DBlocks.HEPHAESTUS_FORGE);
        addDrop(DBlocks.CRATE);
    }
}
