package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.tywrapstudios.deipotentia.registry.DRegistry;
import net.tywrapstudios.deipotentia.registry.DTags;

import java.util.concurrent.CompletableFuture;

public class TagGen {
    public static class Block extends FabricTagProvider.BlockTagProvider {
        public Block(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        protected void generateTags() {
            getOrCreateTagBuilder(DTags.Blocks.NON_STURDY.get())
                    .add(Blocks.AIR,
                            Blocks.GRASS,
                            Blocks.TALL_GRASS,
                            Blocks.WATER,
                            Blocks.CRIMSON_ROOTS,
                            Blocks.CRIMSON_FUNGUS,
                            Blocks.WARPED_ROOTS,
                            Blocks.WARPED_FUNGUS);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            generateTags();
        }
    }

    public static class Item extends FabricTagProvider.ItemTagProvider {
        public Item(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture, null);
        }

        protected void generateTags() {
            getOrCreateTagBuilder(DTags.Items.SICKLES.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE)
                    .add(DRegistry.DItems.STURDY_SICKLE);
            getOrCreateTagBuilder(DTags.Items.NON_STURDY.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE);
            getOrCreateTagBuilder(DTags.Items.REPULSING.get())
                    .add(DRegistry.DItems.ANGELS_GUARD);
            getOrCreateTagBuilder(DTags.Items.RANGED_WEAPONS.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE)
                    .add(DRegistry.DItems.STURDY_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE);
            getOrCreateTagBuilder(DTags.Items.DISPLAYABLE.get())
                    .add(DRegistry.DItems.CRIMSON_SICKLE)
                    .add(DRegistry.DItems.WARPED_SICKLE)
                    .add(DRegistry.DItems.STURDY_SICKLE)
                    .add(DRegistry.DItems.NYMPH_SICKLE);
            getOrCreateTagBuilder(DTags.Items.PLUSH.get())
                    .add(DRegistry.DItems.BLAHAJ)
                    .add(DRegistry.DItems.BLAHAJ_GODS)
                    .add(DRegistry.DItems.BLAHAJ_GODS_DEACTIVATED);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            generateTags();
        }
    }
}
