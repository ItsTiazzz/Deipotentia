package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.tywrapstudios.deipotentia.registry.DRegistry.*;

public class ModelGen extends FabricModelProvider {
    public ModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        TextureMap textureMap = (new TextureMap()).put(TextureKey.PARTICLE, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_front")).put(TextureKey.DOWN, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_bottom")).put(TextureKey.UP, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_top")).put(TextureKey.NORTH, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_front")).put(TextureKey.SOUTH, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_front")).put(TextureKey.EAST, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_side")).put(TextureKey.WEST, TextureMap.getSubId(DBlocks.HEPHAESTUS_FORGE, "_side"));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(DBlocks.HEPHAESTUS_FORGE, Models.CUBE.upload(DBlocks.HEPHAESTUS_FORGE, textureMap, generator.modelCollector)));

        generator.registerWoolAndCarpet(DBlocks.CLOTH, DBlocks.CLOTH_CARPET);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        simpleItem(DItems.VALSOULSTRANGLER, generator);
        simpleItem(DItems.VALSOULSTRANGLER_DEACTIVATED, generator);
        simpleItem(DItems.ANGELS_GUARD, generator);
        simpleItem(DItems.ANGELS_GUARD_DEACTIVATED, generator);
        simpleItem(DItems.SOUL_ITEM, generator);
        simpleItem(DItems.EMPTY_SOUL, generator);
        simpleItem(DItems.SOUL_BLEACHER, generator);
        simpleItem(DItems.STURDY_TEMPLATE, generator);
        simpleItem(DBlocks.CLOTH_CARPET.asItem(), generator);
        simpleItem(DItems.FLAX, generator);
        simpleItem(DItems.WHAT_WE_DID_IN_THE_DESERT_DISC, generator);
    }

    private void simpleItem(Item item, ItemModelGenerator generator) {
        generator.register(item, Models.GENERATED);
    }
}
