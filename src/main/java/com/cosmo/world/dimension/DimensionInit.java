package com.cosmo.world.dimension;

import com.cosmo.CosmicVeil;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.OptionalLong;

public class DimensionInit {
    public static final RegistryKey<World> SHADOW_REALM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(CosmicVeil.MOD_ID,"shadow_realm"));
    public static final RegistryKey<DimensionType> SHADOW_REALM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(CosmicVeil.MOD_ID,"shadow_realm_type"));
    public static final RegistryKey<World> SOLAR_REALM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(CosmicVeil.MOD_ID,"solar_realm"));
    public static final RegistryKey<DimensionType> SOLAR_REALM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(CosmicVeil.MOD_ID,"solar_realm_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(SHADOW_REALM_TYPE,new DimensionType(
                OptionalLong.of(15000),
                false,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                Identifier.of(CosmicVeil.MOD_ID,"shadow_realm"),
                0.0f,
                new DimensionType.MonsterSettings(false,false, UniformIntProvider.create(0,0),0)
        ));
        context.register(SOLAR_REALM_TYPE,new DimensionType(
                OptionalLong.of(15000),
                false,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                256,
                256,
                BlockTags.INFINIBURN_OVERWORLD,
                Identifier.of(CosmicVeil.MOD_ID,"solar_realm"),
                0.0f,
                new DimensionType.MonsterSettings(false,false, UniformIntProvider.create(0,0),0)
        ));
    }
}
