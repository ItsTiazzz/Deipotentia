package net.tywrapstudios.deipotentia.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DeipotentiaDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ModelGen::new);
		fabricDataGenerator.addProvider(TagGen.Block::new);
		fabricDataGenerator.addProvider(TagGen.Item::new);
	}
}
