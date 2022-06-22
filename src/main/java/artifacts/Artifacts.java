package artifacts;

import artifacts.common.compat.CompatHandler;
import artifacts.common.config.ConfigHelper;
import artifacts.common.config.ModConfig;
import artifacts.common.init.ModFeatures;
import artifacts.common.init.ModItems;
import artifacts.common.init.ModLootConditions;
import artifacts.common.init.ModLootTables;
import artifacts.common.init.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Artifacts implements ModInitializer {

	public static final String MODID = "artifacts";
	public static final Logger LOGGER = LoggerFactory.getLogger(Artifacts.class);
	public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.build(
			id("item_group"),
			() -> new ItemStack(ModItems.BUNNY_HOPPERS)
	);
	public static ModConfig CONFIG;

	// The current/required version of the config file format
	// Increase this if the config format has changed in an incompatible way
	// When the game is loaded with an older config version, it will reset all values to their defaults
	public static final int CONFIG_VERSION = 1;

	@Override
	@SuppressWarnings("ResultOfMethodCallIgnored")
	public void onInitialize() {
		// Config
		CONFIG = ConfigHelper.getConfigAndInvalidateOldVersions();

		// Loot table setup
		ModLootConditions.register();
		LootTableEvents.MODIFY.register((rm, lt, id, supplier, s) ->
				ModLootTables.onLootTableLoad(id, supplier));

		// Force loading init classes
		// Entities is loaded by items, loot tables can load lazily (no registration)
		ModItems.ANTIDOTE_VESSEL.toString();
		ModSoundEvents.MIMIC_CLOSE.toString();
		ModFeatures.register();

		runCompatibilityHandlers();
		LOGGER.info("Finished initialization");
	}

	private void runCompatibilityHandlers() {
		FabricLoader.getInstance().getEntrypoints("artifacts:compat_handlers", CompatHandler.class).stream()
				.filter(h -> FabricLoader.getInstance().isModLoaded(h.getModId()))
				.forEach(ch -> {
					String modName = FabricLoader.getInstance().getModContainer(ch.getModId())
							.map(c -> c.getMetadata().getName())
							.orElse(ch.getModId());
					LOGGER.info("Running compat handler for " + modName);

					ch.run();
				});
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}
}
