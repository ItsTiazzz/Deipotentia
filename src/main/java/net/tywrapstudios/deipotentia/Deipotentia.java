package net.tywrapstudios.deipotentia;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.tywrapstudios.blossombridge.api.config.ConfigManager;
import net.tywrapstudios.blossombridge.api.logging.LoggingHandler;
import net.tywrapstudios.deipotentia.config.DeiConfig;
import net.tywrapstudios.deipotentia.enchantment.SoulBoundEnchantment;
import net.tywrapstudios.deipotentia.loot.LootStuffIdkWhatToCallThisClass;
import net.tywrapstudios.deipotentia.registry.*;
import net.tywrapstudios.deipotentia.item.SoulItem;
import net.tywrapstudios.deipotentia.util.TickScheduler;

import java.io.File;

public class Deipotentia implements ModInitializer {
	public static final String MOD_ID = "deipotentia";
	public static final ConfigManager<DeiConfig> CONFIG_MANAGER = new ConfigManager<>(DeiConfig.class, new File(FabricLoader.getInstance().getConfigDir().toFile(), "deipotentia.json5"));
	public static LoggingHandler<DeiConfig> LOGGING = new LoggingHandler<>("Deipotentia", CONFIG_MANAGER);

	@Override
	public void onInitialize() {
		CONFIG_MANAGER.loadConfig();
		CONFIG_MANAGER.getConfig().util_config.debug_mode = true;
		CONFIG_MANAGER.saveConfig();

		DRegistry.registerAll();
		DBlockEntities.register();
		DScreenHandlers.register();
		DRecipes.register();
		DItemGroup.register();
		DEnchantments.register();
		DSounds.register();
		LOGGING.debug("Registered Content.");

		SoulItem.Logic.initialize();
		SoulBoundEnchantment.Logic.initialize();
		TickScheduler.initialize();
		LootStuffIdkWhatToCallThisClass.initialize();
		LOGGING.debug("Initialized Logic.");

		LOGGING.info("May your woes have Deipotentia, and their wishes be granted.");
		LOGGING.debugWarning("You enabled [Debug Mode] in the Config. Beware that this may occasionally spam your logs full of junk check results!");
	}
}