package com.cosmo.world.feature;

import com.cosmo.CosmicVeil;
import com.cosmo.init.BlockInit;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class CosmicVeilConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?,?>> DARK_PILLAR_KEY = registerKey("dark_pillar");
    public static final RegistryKey<ConfiguredFeature<?,?>> DARK_SPIRE_KEY = registerKey("dark_spire");
    public static final RegistryKey<ConfiguredFeature<?,?>> SHADOW_MONARCHS_TREASURE_KEY = registerKey("shadow_monarchs_treasure");
    public static final RegistryKey<ConfiguredFeature<?,?>> SHADOW_PORTAL_KEY = registerKey("shadow_portal");
    public static final RegistryKey<ConfiguredFeature<?,?>> SHADROCK_ORE_KEY = registerKey("shadrock_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> ECLIPSIUM_ORE_KEY = registerKey("eclipsium_ore");
    public static final RegistryKey<ConfiguredFeature<?,?>> ASHENITE_ORE_KEY = registerKey("ashenite_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?,?>> context) {
        register(context, DARK_PILLAR_KEY, CosmicVeil.DarkPillar,FeatureConfig.DEFAULT);
        register(context, DARK_SPIRE_KEY, CosmicVeil.DarkSpire,FeatureConfig.DEFAULT);
        register(context, SHADOW_MONARCHS_TREASURE_KEY, CosmicVeil.ShadowMonarchsTreasure,FeatureConfig.DEFAULT);
        register(context, SHADOW_PORTAL_KEY, CosmicVeil.ShadowPortal,FeatureConfig.DEFAULT);

        RuleTest DarkShadowRockReplaceable = new BlockMatchRuleTest(BlockInit.DarkShadowRock);
        RuleTest LightShadowRockReplaceable = new BlockMatchRuleTest(BlockInit.LightShadowRock);
        RuleTest RedShadowRockReplaceable = new BlockMatchRuleTest(BlockInit.RedShadowRock);

        List<OreFeatureConfig.Target> shadrockOres =
                List.of(
                        OreFeatureConfig.createTarget(DarkShadowRockReplaceable,BlockInit.DarkShadrockOre.getDefaultState()),
                        OreFeatureConfig.createTarget(LightShadowRockReplaceable,BlockInit.LightShadrockOre.getDefaultState()),
                        OreFeatureConfig.createTarget(RedShadowRockReplaceable,BlockInit.RedShadrockOre.getDefaultState())
                );
        List<OreFeatureConfig.Target> eclipsiumOres =
                List.of(
                        OreFeatureConfig.createTarget(DarkShadowRockReplaceable,BlockInit.DarkEclipsiumOre.getDefaultState()),
                        OreFeatureConfig.createTarget(LightShadowRockReplaceable,BlockInit.LightEclipsiumOre.getDefaultState()),
                        OreFeatureConfig.createTarget(RedShadowRockReplaceable,BlockInit.RedEclipsiumOre.getDefaultState())
                );
        List<OreFeatureConfig.Target> asheniteOres =
                List.of(
                        OreFeatureConfig.createTarget(DarkShadowRockReplaceable,BlockInit.DarkAsheniteOre.getDefaultState()),
                        OreFeatureConfig.createTarget(LightShadowRockReplaceable,BlockInit.LightAsheniteOre.getDefaultState()),
                        OreFeatureConfig.createTarget(RedShadowRockReplaceable,BlockInit.RedAsheniteOre.getDefaultState())
                );
        register(context, SHADROCK_ORE_KEY,Feature.ORE,new OreFeatureConfig(shadrockOres,5));
        register(context, ASHENITE_ORE_KEY,Feature.ORE,new OreFeatureConfig(asheniteOres,5));
        register(context, ECLIPSIUM_ORE_KEY,Feature.ORE,new OreFeatureConfig(eclipsiumOres,5));
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(CosmicVeil.MOD_ID,name));
    }
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?,?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?,?>> key,F feature, FC configuration) {
        context.register(key,new ConfiguredFeature<>(feature,configuration));

    }
}
