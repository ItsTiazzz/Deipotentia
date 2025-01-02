package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.tywrapstudios.deipotentia.registry.DRegistry.*;
import net.tywrapstudios.deipotentia.registry.DTags;

import java.util.concurrent.CompletableFuture;

public class TagGen {
    public static class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
        public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
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

    public static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture, null);
        }

        protected void generateTags() {
            getOrCreateTagBuilder(DTags.Items.SICKLES.get())
                    .add(DItems.CRIMSON_SICKLE)
                    .add(DItems.NYMPH_SICKLE)
                    .add(DItems.WARPED_SICKLE)
                    .add(DItems.STURDY_SICKLE);
            getOrCreateTagBuilder(DTags.Items.NON_STURDY.get())
                    .add(DItems.CRIMSON_SICKLE)
                    .add(DItems.NYMPH_SICKLE)
                    .add(DItems.WARPED_SICKLE);
            getOrCreateTagBuilder(DTags.Items.REPULSING.get())
                    .add(DItems.ANGELS_GUARD);
            getOrCreateTagBuilder(DTags.Items.RANGED_WEAPONS.get())
                    .add(DItems.CRIMSON_SICKLE)
                    .add(DItems.WARPED_SICKLE)
                    .add(DItems.STURDY_SICKLE)
                    .add(DItems.NYMPH_SICKLE);
            getOrCreateTagBuilder(DTags.Items.DISPLAYABLE.get())
                    .add(DItems.CRIMSON_SICKLE)
                    .add(DItems.WARPED_SICKLE)
                    .add(DItems.STURDY_SICKLE)
                    .add(DItems.NYMPH_SICKLE);
            getOrCreateTagBuilder(DTags.Items.PLUSH.get())
                    .add(DItems.BLAHAJ)
                    .add(DItems.BLAHAJ_GODS)
                    .add(DItems.BLAHAJ_GODS_DEACTIVATED);
            getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                    .add(DItems.WHAT_WE_DID_IN_THE_DESERT_DISC);
            getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                    .add(DItems.WHAT_WE_DID_IN_THE_DESERT_DISC);
            getOrCreateTagBuilder(DTags.Items.RETAIN_DURABILITY.get())
                    .add(DItems.VALSOULSTRANGLER);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            generateTags();
        }
    }
}
