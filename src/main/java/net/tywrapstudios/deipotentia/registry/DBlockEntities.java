package net.tywrapstudios.deipotentia.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.Deipotentia;
import net.tywrapstudios.deipotentia.block.HephaestusForgeBlockEntity;

public class DBlockEntities {
    public static BlockEntityType<HephaestusForgeBlockEntity> HEPHAESTUS_FORGE;

    public static void register() {
        HEPHAESTUS_FORGE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(Deipotentia.MOD_ID, "hephaestus_forge"),
                FabricBlockEntityTypeBuilder.create(HephaestusForgeBlockEntity::new,
                        DRegistry.DBlocks.HEPHAESTUS_FORGE).build(null));
    }
}
