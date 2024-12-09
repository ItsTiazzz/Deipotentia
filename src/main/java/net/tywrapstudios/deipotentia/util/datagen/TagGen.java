package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import net.tywrapstudios.deipotentia.registry.DTags;

public class TagGen {
    public static class Block extends FabricTagProvider.BlockTagProvider {
        public Block(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(DTags.Blocks.NON_STURDY.get())
                    .add(Blocks.AIR,
                            Blocks.GRASS,
                            Blocks.TALL_GRASS,
                            Blocks.WATER,
                            Blocks.CRIMSON_ROOTS,
                            Blocks.CRIMSON_FUNGUS,
                            Blocks.WARPED_ROOTS,
                            Blocks.WARPED_FUNGUS);
        }
    }

    public static class Item extends FabricTagProvider.ItemTagProvider {
        public Item(FabricDataGenerator dataGenerator) {
            super(dataGenerator, null);
        }

        @Override
        protected void generateTags() {
            this.getOrCreateTagBuilder(DTags.Items.SICKLES.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE)
                    .add(DRegistry.DItems.STURDY_SICKLE);
            this.getOrCreateTagBuilder(DTags.Items.NON_STURDY.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE);
            this.getOrCreateTagBuilder(DTags.Items.REPULSING.get())
                    .add(DRegistry.DItems.ANGELS_GUARD);
        }
    }
}
