package com.cosmo.world.gen;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.init.EntityInit;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public class CosmicVeilEntityGeneration {
    public static void addSpawns(){
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(RegistryKey.of(RegistryKeys.BIOME,new Identifier(CosmicVeil.MOD_ID,"shadow_plains"))), SpawnGroup.MONSTER,
                EntityInit.WEEPER,1,1,3);

        SpawnRestriction.register(EntityInit.WEEPER, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WeeperEntity::canSpawnInDark);

    }
}
