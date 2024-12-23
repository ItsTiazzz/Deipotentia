package net.tywrapstudios.deipotentia.loot;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.tywrapstudios.deipotentia.registry.DRegistry;

public class LootStuffIdkWhatToCallThisClass {
    public static void initialize() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if ((id.equals(new Identifier("blocks/grass")) || id.equals(new Identifier("blocks/tall_grass")))) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(DRegistry.DItems.FLAX)
                                    .conditionally(RandomChanceLootCondition.builder(0.125f)));

                    tableBuilder.pool(poolBuilder);
                }

                if (id.equals(new Identifier("chests/village/village_weaponsmith"))) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1))
                            .with(ItemEntry.builder(DRegistry.DItems.STURDY_TEMPLATE));
                    tableBuilder.pool(poolBuilder);
                }
            }
        });
    }
}
