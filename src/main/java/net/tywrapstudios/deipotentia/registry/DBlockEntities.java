package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.block.HephaestusForgeBlockEntity;

public class DBlockEntities {
    public static BlockEntityType<HephaestusForgeBlockEntity> HEPHAESTUS_FORGE;

    public static void register() {
        HEPHAESTUS_FORGE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Deipotentia.MOD_ID, "hephaestus_forge"),
                FabricBlockEntityTypeBuilder.create(HephaestusForgeBlockEntity::new,
                        DRegistry.DBlocks.HEPHAESTUS_FORGE).build(null));
    }
}
