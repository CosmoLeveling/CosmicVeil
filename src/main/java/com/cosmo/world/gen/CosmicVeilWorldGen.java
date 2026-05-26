package com.cosmo.world.gen;

import com.cosmo.CosmicVeil;
import com.cosmo.world.generator.ShadowRealmChunkGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CosmicVeilWorldGen {
    public static void generateWorldGen(){
        Registry.register(Registries.CHUNK_GENERATOR,new Identifier(CosmicVeil.MOD_ID, "shadow_chunkgen"), ShadowRealmChunkGenerator.CODEC);
    }
}
