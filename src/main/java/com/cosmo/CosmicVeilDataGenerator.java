package com.cosmo;

import com.cosmo.datagen.*;
import com.cosmo.world.dimension.DimensionInit;
import com.cosmo.world.feature.CosmicVeilConfiguredFeatures;
import com.cosmo.world.feature.CosmicVeilPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class CosmicVeilDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(CosmicVeilModelProvider::new);
		pack.addProvider(CosmicVeilEnusLanguageProvider::new);
		pack.addProvider(CosmicVeilRecipeProvider::new);
		pack.addProvider(CosmicVeilBlockTagProvider::new);
		pack.addProvider(CosmicVeilBlockLootProvider::new);
		pack.addProvider(CosmicVeilWorldGenerator::new);
		pack.addProvider(CosmicVeilFusionModelProvider::new);
        pack.addProvider(CosmicVeilAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.DIMENSION_TYPE, DimensionInit::bootstrapType);
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, CosmicVeilConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, CosmicVeilPlacedFeatures::bootstrap);
	}
}
