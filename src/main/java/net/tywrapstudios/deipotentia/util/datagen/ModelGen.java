package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.registry.DRegistry.*;

public class ModelGen extends FabricModelProvider {
    public ModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        smithingTableLike(DBlocks.HEPHAESTUS_FORGE, generator);
        generator.registerWoolAndCarpet(DBlocks.CLOTH, DBlocks.CLOTH_CARPET);
        barrelLike(DBlocks.CRATE, generator);
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

    private void smithingTableLike(Block block, BlockStateModelGenerator generator) {
        TextureMap textureMap = (new TextureMap())
                .put(TextureKey.PARTICLE, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.DOWN, TextureMap.getSubId(block, "_bottom"))
                .put(TextureKey.UP, TextureMap.getSubId(block, "_top"))
                .put(TextureKey.NORTH, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.SOUTH, TextureMap.getSubId(block, "_front"))
                .put(TextureKey.EAST, TextureMap.getSubId(block, "_side"))
                .put(TextureKey.WEST, TextureMap.getSubId(block, "_side"));
        generator.blockStateCollector.accept(BlockStateModelGenerator
                .createSingletonBlockState(block, Models.CUBE.upload(block, textureMap, generator.modelCollector)));
    }

    private void barrelLike(Block block, BlockStateModelGenerator generator) {
        Identifier identifier = TextureMap.getSubId(block, "_top_open");
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(generator.createUpDefaultFacingVariantMap())
                .coordinate(BlockStateVariantMap.create(Properties.OPEN)
                        .register(false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.upload(block, generator.modelCollector)))
                        .register(true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, TexturedModel.CUBE_BOTTOM_TOP.get(block).textures((textureMap) -> textureMap.put(TextureKey.TOP, identifier)).upload(block, "_open", generator.modelCollector)))));
    }
}
