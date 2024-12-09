package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.block.HephaestusForgeBlock;
import net.tywrapstudios.deipotentia.item.AngelsGuardItem;
import net.tywrapstudios.deipotentia.item.Deactivated;
import net.tywrapstudios.deipotentia.item.SoulItem;
import net.tywrapstudios.deipotentia.item.ValSoulStranglerItem;
import net.tywrapstudios.deipotentia.item.sickles.CrimsonSickleItem;
import net.tywrapstudios.deipotentia.item.sickles.NymphSickleItem;
import net.tywrapstudios.deipotentia.item.sickles.SturdySickleItem;
import net.tywrapstudios.deipotentia.item.sickles.WarpedSickleItem;

import static net.tywrapstudios.deipotentia.registry.DItemGroup.DEI_GROUP;

public class DRegistry {
    public static class DItems {
        public static final Item VALSOULSTRANGLER;
        public static final Item VALSOULSTRANGLER_DEACTIVATED;
        public static final Item WARPED_SICKLE;
        public static final Item CRIMSON_SICKLE;
        public static final Item STURDY_SICKLE;
        public static final Item NYMPH_SICKLE;
        public static final Item ANGELS_GUARD;
        public static final Item ANGELS_GUARD_DEACTIVATED;
        public static final Item SOUL_ITEM;

        static {
            VALSOULSTRANGLER = create("valsoulstrangler", new ValSoulStranglerItem(new FabricItemSettings().group(DEI_GROUP)
                    .maxCount(1)
                    .maxDamage(6)));
            VALSOULSTRANGLER_DEACTIVATED = create("valsoulstrangler_deactivated", new Deactivated.ValSoulStrangler(new FabricItemSettings().group(DEI_GROUP)
                    .maxCount(1)));
            WARPED_SICKLE = create("warped_sickle", new WarpedSickleItem(new FabricItemSettings().group(DEI_GROUP)));
            CRIMSON_SICKLE = create("crimson_sickle", new CrimsonSickleItem(new FabricItemSettings().group(DEI_GROUP)));
            STURDY_SICKLE = create("sturdy_sickle", new SturdySickleItem(new FabricItemSettings().group(DEI_GROUP)));
            NYMPH_SICKLE = create("nymph_sickle", new NymphSickleItem(new FabricItemSettings().group(DEI_GROUP)));
            ANGELS_GUARD = create("angels_guard", new AngelsGuardItem(new FabricItemSettings().group(DEI_GROUP)
                    .maxCount(1)));
            ANGELS_GUARD_DEACTIVATED = create("angels_guard_deactivated", new Deactivated.AngelsGuard(new FabricItemSettings().group(DEI_GROUP)
                    .maxCount(1)));
            SOUL_ITEM = create("soul_item", new SoulItem(new FabricItemSettings().group(DEI_GROUP)
                    .maxCount(1)));
        }

        private static Item create(String name, Item item) {
            return Registry.register(Registry.ITEM, new Identifier(Deipotentia.MOD_ID, name), item);
        }

        public static void register() {
        }
    }

    public static class DBlocks {
        public static final Block HEPHAESTUS_FORGE;

        static {
            HEPHAESTUS_FORGE = create("hephaestus_forge", new HephaestusForgeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
        }

        private static Block create(String name, Block block) {
            createBlockItem(name, block);
            return Registry.register(Registry.BLOCK, new Identifier(Deipotentia.MOD_ID, name), block);
        }

        private static void createBlockItem(String name, Block block) {
            Registry.register(Registry.ITEM, new Identifier(Deipotentia.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(DEI_GROUP)));
        }

        public static void register() {
        }
    }
}
