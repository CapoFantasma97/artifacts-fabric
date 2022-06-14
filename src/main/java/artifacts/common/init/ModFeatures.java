package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.world.CampsiteFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import java.util.List;

import static artifacts.Artifacts.CONFIG;

public class ModFeatures {

	public static final Feature<NoneFeatureConfiguration> CAMPSITE = Registry.register(
			Registry.FEATURE,
			Artifacts.id("campsite"),
			new CampsiteFeature()
	);
	public static final PlacedFeature PLACED_CAMPSITE;

	public static void register() {
		if (CONFIG.worldgen.campsite.campsiteRarity < 10_000) {
			BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
					GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
					BuiltinRegistries.PLACED_FEATURE.getResourceKey(PLACED_CAMPSITE)
							.orElseThrow(() -> new RuntimeException("Failed to get feature from registry")));
		}
	}

	static {
		ConfiguredFeature<?, ?> configuredFeature = Registry.register(
				BuiltinRegistries.CONFIGURED_FEATURE,
				Artifacts.id("campsite"),
				new ConfiguredFeature<>(CAMPSITE, FeatureConfiguration.NONE)
		);
		ResourceKey<ConfiguredFeature<?, ?>> featureKey = BuiltinRegistries.CONFIGURED_FEATURE.getResourceKey(configuredFeature).orElseThrow();
		Holder<ConfiguredFeature<?, ?>> featureHolder = BuiltinRegistries.CONFIGURED_FEATURE.getHolderOrThrow(featureKey);

		PLACED_CAMPSITE = Registry.register(
				BuiltinRegistries.PLACED_FEATURE,
				Artifacts.id("underground_campsite"),
				new PlacedFeature(featureHolder,
						List.of(RarityFilter.onAverageOnceEvery(CONFIG.worldgen.campsite.campsiteRarity),
							InSquarePlacement.spread(),
							HeightRangePlacement.uniform(
									VerticalAnchor.aboveBottom(32),
									VerticalAnchor.aboveBottom(96)
							),
							EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 32),
							RandomOffsetPlacement.vertical(ConstantInt.of(1)),
							BiomeFilter.biome())
				)
		);
	}
}
