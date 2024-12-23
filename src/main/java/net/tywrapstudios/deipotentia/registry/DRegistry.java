package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.block.BlahajBlock;
import net.tywrapstudios.deipotentia.block.HephaestusForgeBlock;
import net.tywrapstudios.deipotentia.item.*;
import net.tywrapstudios.deipotentia.item.deactivated.DeactivatedAG;
import net.tywrapstudios.deipotentia.item.deactivated.DeactivatedBH;
import net.tywrapstudios.deipotentia.item.deactivated.DeactivatedVSS;
import net.tywrapstudios.deipotentia.item.sickles.CrimsonSickleItem;
import net.tywrapstudios.deipotentia.item.sickles.NymphSickleItem;
import net.tywrapstudios.deipotentia.item.sickles.SturdySickleItem;
import net.tywrapstudios.deipotentia.item.sickles.WarpedSickleItem;

import java.util.ArrayList;
import java.util.List;

import static net.tywrapstudios.deipotentia.registry.DRegistry.DBlocks.*;
import static net.tywrapstudios.deipotentia.registry.DRegistry.DItems.*;

public class DRegistry {
    public static final List<ItemConvertible> ITEM_CONVERTIBLES = new ArrayList<>();

    public static class DItems {
        public static List<Item> ITEMS = new ArrayList<>();

        public static final Item VALSOULSTRANGLER;
        public static final Item VALSOULSTRANGLER_DEACTIVATED;
        public static final Item WARPED_SICKLE;
        public static final Item CRIMSON_SICKLE;
        public static final Item STURDY_SICKLE;
        public static final Item NYMPH_SICKLE;
        public static final Item ANGELS_GUARD;
        public static final Item ANGELS_GUARD_DEACTIVATED;
        public static final Item SOUL_ITEM;
        public static final Item SOUL_BLEACHER;
        public static final Item EMPTY_SOUL;
        public static final Item STURDY_TEMPLATE;
        public static final Item FLAX;
        public static final Item BLAHAJ;
        public static final Item BLAHAJ_GODS;
        public static final Item BLAHAJ_GODS_DEACTIVATED;
        public static final Item WHAT_WE_DID_IN_THE_DESERT_DISC;

        static {
            VALSOULSTRANGLER = create("valsoulstrangler", new ValSoulStranglerItem(new FabricItemSettings()
                    .maxCount(1)
                    .maxDamage(6)));
            VALSOULSTRANGLER_DEACTIVATED = create("valsoulstrangler_deactivated", new DeactivatedVSS(new FabricItemSettings()));
            WARPED_SICKLE = create("warped_sickle", new WarpedSickleItem(new FabricItemSettings()));
            CRIMSON_SICKLE = create("crimson_sickle", new CrimsonSickleItem(new FabricItemSettings()
                    .fireproof()));
            STURDY_SICKLE = create("sturdy_sickle", new SturdySickleItem(new FabricItemSettings()
                    .fireproof()));
            NYMPH_SICKLE = create("nymph_sickle", new NymphSickleItem(new FabricItemSettings()));
            ANGELS_GUARD = create("angels_guard", new AngelsGuardItem(new FabricItemSettings()
                    .maxCount(1)));
            ANGELS_GUARD_DEACTIVATED = create("angels_guard_deactivated", new DeactivatedAG(new FabricItemSettings()));
            SOUL_ITEM = create("soul_item", new SoulItem(new FabricItemSettings()
                    .maxCount(1)));
            SOUL_BLEACHER = create("soul_bleacher", new SoulBleacherItem(new FabricItemSettings()
                    .maxCount(1)));
            EMPTY_SOUL = create("empty_soul", new Item(new FabricItemSettings()
                    .maxCount(16)));
            STURDY_TEMPLATE = create("sturdy_template", new Item(new FabricItemSettings()));
            FLAX = create("flax", new Item(new FabricItemSettings()));
            BLAHAJ = create("blahaj", new BlahajItem(BLAHAJ_BLOCK, new FabricItemSettings()));
            BLAHAJ_GODS = create("blahaj_gods", new BlahajGodsItem(new FabricItemSettings()));
            BLAHAJ_GODS_DEACTIVATED = create("blahaj_gods_deactivated", new DeactivatedBH(new FabricItemSettings()));
            WHAT_WE_DID_IN_THE_DESERT_DISC = create("what_we_did_in_the_desert_disc", new MusicDiscItem(7, DSounds.WWDITD,
                    new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 267));
        }

        private static Item create(String name, Item item) {
            ITEMS.add(item);
            return Registry.register(Registries.ITEM, Deipotentia.id(name), item);
        }

        public static List<Item> register() {
            return ITEMS;
        }
    }

    public static class DBlocks {
        public static List<Block> BLOCKS = new ArrayList<>();

        public static final Block HEPHAESTUS_FORGE;
        public static final Block CLOTH;
        public static final Block CLOTH_CARPET;
        public static final Block BLAHAJ_BLOCK;

        static {
            HEPHAESTUS_FORGE = create("hephaestus_forge", new HephaestusForgeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
            CLOTH = create("cloth", new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)));
            CLOTH_CARPET = create("cloth_carpet", new CarpetBlock((FabricBlockSettings.copyOf(Blocks.WHITE_CARPET))));
            BLAHAJ_BLOCK = create("blahaj", new BlahajBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)), false);
        }

        private static Block create(String name, Block block) {
            BLOCKS.add(block);
            createBlockItem(name, block);
            return Registry.register(Registries.BLOCK, Deipotentia.id(name), block);
        }

        private static Block create(String name, Block block, boolean includeItem) {
            if (includeItem) {
                BLOCKS.add(block);
                createBlockItem(name, block);
            }
            return Registry.register(Registries.BLOCK, Deipotentia.id(name), block);
        }

        private static void createBlockItem(String name, Block block) {
            Registry.register(Registries.ITEM, Deipotentia.id(name), new BlockItem(block, new FabricItemSettings()));
        }

        public static List<Block> register() {
            return BLOCKS;
        }
    }

    public static List<ItemConvertible> registerAll() {
        ITEM_CONVERTIBLES.addAll(DItems.register());
        ITEM_CONVERTIBLES.addAll(DBlocks.register());
        return ITEM_CONVERTIBLES;
    }
}
