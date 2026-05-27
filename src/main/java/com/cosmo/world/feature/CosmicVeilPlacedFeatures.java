package com.cosmo.world.feature;

import com.cosmo.CosmicVeil;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class CosmicVeilPlacedFeatures {
    public static final RegistryKey<PlacedFeature> DARK_PILLAR_PLACED_KEY = registryKey("dark_pillar_placed");
    public static final RegistryKey<PlacedFeature> DARK_SPIRE_PLACED_KEY = registryKey("dark_spire_placed");
    public static final RegistryKey<PlacedFeature> SOLAR_SPIRE_PLACED_KEY = registryKey("solar_spire_placed");
    public static final RegistryKey<PlacedFeature> SHADOW_MONARCHS_TREASURE_PLACED_KEY = registryKey("shadow_monarchs_treasure_placed");
    public static final RegistryKey<PlacedFeature> SHADOW_PORTAL_PLACED_KEY = registryKey("shadow_portal_placed");
    public static final RegistryKey<PlacedFeature> SHADROCK_ORE_PLACED_KEY = registryKey("shadrock_ore_placed");
    public static final RegistryKey<PlacedFeature> ASHENITE_ORE_PLACED_KEY = registryKey("ashenite_ore_placed");
    public static final RegistryKey<PlacedFeature> ECLIPSIUM_ORE_PLACED_KEY = registryKey("eclipsium_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context,SHADROCK_ORE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.SHADROCK_ORE_KEY),
                CosmicVeilOrePlacement.modifiersWithCount(2,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-65),YOffset.fixed(90))));
        register(context,ASHENITE_ORE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.ASHENITE_ORE_KEY),
                CosmicVeilOrePlacement.modifiersWithCount(2,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-65),YOffset.fixed(90))));
        register(context,ECLIPSIUM_ORE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.ECLIPSIUM_ORE_KEY),
                CosmicVeilOrePlacement.modifiersWithCount(2,
                       HeightRangePlacementModifier.uniform(YOffset.fixed(-65),YOffset.fixed(90))));
        register(context, DARK_PILLAR_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.DARK_PILLAR_KEY),
                List.of(
                        CountPlacementModifier.of(1), // spawn count per chunk
                        SquarePlacementModifier.of(),
                        RarityFilterPlacementModifier.of(3),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-65), YOffset.fixed(90)),
                        BiomePlacementModifier.of()
                ));
        register(context, DARK_SPIRE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.DARK_SPIRE_KEY),
                List.of(
                        CountPlacementModifier.of(1), // spawn count per chunk
                        SquarePlacementModifier.of(),
                        RarityFilterPlacementModifier.of(100),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(85), YOffset.fixed(90)),
                        BiomePlacementModifier.of()
                ));
        register(context, SOLAR_SPIRE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.SOLAR_SPIRE_KEY),
                List.of(
                        CountPlacementModifier.of(1), // spawn count per chunk
                        SquarePlacementModifier.of(),
                        RarityFilterPlacementModifier.of(100),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(85), YOffset.fixed(90)),
                        BiomePlacementModifier.of()
                ));
        register(context, SHADOW_MONARCHS_TREASURE_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.SHADOW_MONARCHS_TREASURE_KEY),
                List.of(
                        CountPlacementModifier.of(1), // spawn count per chunk
                        SquarePlacementModifier.of(),
                        RarityFilterPlacementModifier.of(200),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-65), YOffset.fixed(90)),
                        BiomePlacementModifier.of()
                ));
        register(context, SHADOW_PORTAL_PLACED_KEY,configuredFeatureRegistryEntryLookup.getOrThrow(CosmicVeilConfiguredFeatures.SHADOW_PORTAL_KEY),
                List.of(
                        CountPlacementModifier.of(1), // spawn count per chunk
                        SquarePlacementModifier.of(),
                        RarityFilterPlacementModifier.of(200),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-56), YOffset.fixed(90)),
                        BiomePlacementModifier.of()
                ));
    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE,new Identifier(CosmicVeil.MOD_ID,name));
    }
    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?,?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
