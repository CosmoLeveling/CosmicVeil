package com.cosmo.world.generator;

import com.cosmo.init.BlockInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.LocalRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SolarRealmChunkGenerator extends ChunkGenerator {

    public static final Codec<SolarRealmChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(SolarRealmChunkGenerator::getBiomeSource),
            Codec.INT.fieldOf("world_height").forGetter(SolarRealmChunkGenerator::getWorldHeight)
    ).apply(instance, SolarRealmChunkGenerator::new));

    private final int worldHeight;

    // Kept as references, but initialized dynamically per-seed to avoid cross-world leaks
    private PerlinNoiseSampler floorNoise;
    private PerlinNoiseSampler islandNoise;
    private PerlinNoiseSampler heightNoise;
    private PerlinNoiseSampler blobNoise;
    private long initializedSeed = Long.MIN_VALUE;

    public SolarRealmChunkGenerator(BiomeSource biomeSource, int worldHeight) {
        super(biomeSource);
        this.worldHeight = worldHeight;
        // Fallback default setup
        setupSamplers(12345L);
    }

    private synchronized void setupSamplers(long seed) {
        if (this.initializedSeed == seed) return;
        this.initializedSeed = seed;
        this.floorNoise = new PerlinNoiseSampler(Random.create(seed));
        this.islandNoise = new PerlinNoiseSampler(Random.create(seed + 100));
        this.heightNoise = new PerlinNoiseSampler(Random.create(seed + 200));
        this.blobNoise = new PerlinNoiseSampler(Random.create(seed + 300));
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }


    @Override
    public void carve(ChunkRegion region, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess,
                      StructureAccessor accessor, Chunk chunk, GenerationStep.Carver carverStep) {}

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {}
    private long getSeedFromConfig(NoiseConfig config) {
        try {
            // "legacyWorldSeed" is the Yarn mapping name.
            // If testing in production, use the Intermediary name "field_38255" instead if it fails.
            java.lang.reflect.Field seedField = NoiseConfig.class.getDeclaredField("legacyWorldSeed");
            seedField.setAccessible(true);
            return seedField.getLong(config);
        } catch (Exception e) {
            // Fallback safety seed if the environment uses custom or broken mappings
            return 12345L;
        }
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender,
                                                  NoiseConfig noiseConfig, StructureAccessor accessor,
                                                  Chunk chunk) {
        // Dynamically initialize samplers using the actual world generation seed
        setupSamplers(getSeedFromConfig(noiseConfig));

        ChunkPos chunkPos = chunk.getPos();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        // Thread-safe random allocated purely for this specific chunk thread invocation pass
        Random threadSafeRandom = new LocalRandom(chunkPos.toLong() ^getSeedFromConfig(noiseConfig));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                int worldX = chunkPos.getStartX() + x;
                int worldZ = chunkPos.getStartZ() + z;

                if ((worldZ * worldZ) + (worldX * worldX) <= 128) {
                    continue;
                }

                // 🌍 Large island map (LOW frequency = smoother islands)
                double island = islandNoise.sample(worldX * 0.008, 0, worldZ * 0.008);
                if (island < 0.25) continue;

                // 🎯 Island vertical placement
                int baseHeight = 90 + (int)(heightNoise.sample(worldX * 0.015, 0, worldZ * 0.015) * 12);

                // 📏 Thickness
                int thickness = (int)(island * 20) + 8;

                for (int y = baseHeight + thickness; y >= baseHeight - thickness; y--) {
                    pos.set(worldX, y, worldZ);

                    // 🔥 Smooth vertical curve (stronger falloff = cleaner shape)
                    double dy = (double)(y - baseHeight) / thickness;
                    double shape = island - (dy * dy * 1.3);

                    // 🌊 LOW frequency blob shaping (less noise)
                    double blob = blobNoise.sample(worldX * 0.03, y * 0.03, worldZ * 0.03) * 0.25;

                    // tiny detail layer
                    double detail = blobNoise.sample(worldX * 0.08, y * 0.08, worldZ * 0.08) * 0.08;

                    if (shape + blob + detail > 0.1) {
                        placeNoiseBlocks(chunk, pos, worldX, worldZ, threadSafeRandom);
                    }
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    // Pass thread safe local random state as an execution context parameter directly
    private void placeNoiseBlocks(Chunk chunk, BlockPos blockPos, float worldX, float worldZ, Random chunkRandom) {
        double scale = 0.05;

        // ===== base structure noise =====
        double baseNoise = floorNoise.sample(
                worldX * scale,
                blockPos.getY() * scale,
                worldZ * scale
        );

        // ===== FIXED: Thread safe random call specific to current processing thread context =====
        double randomOffset = (double) chunkRandom.nextBetween(-1, 1) / 10.0;

        // ===== spatial chaos so it blends =====
        double chaosNoise = floorNoise.sample(
                worldX * scale * 2 + 300,
                blockPos.getY() * scale * 2 + 300,
                worldZ * scale * 2 + 300
        ) * 0.12;

        // ===== combine =====
        double value = baseNoise + randomOffset + chaosNoise;

        // ===== block palette injection =====
        if (value > 0) {
            chunk.setBlockState(blockPos, BlockInit.DarkSolarRock.getDefaultState(), false);
        } else if (value > -0.2) {
            chunk.setBlockState(blockPos, BlockInit.SolarRock.getDefaultState(), false);
        } else {
            chunk.setBlockState(blockPos, BlockInit.LightSolarRock.getDefaultState(), false);
        }
    }

    @Override
    public int getWorldHeight() {
        return worldHeight;
    }

    @Override
    public int getSeaLevel() {
        return -64;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        setupSamplers(getSeedFromConfig(noiseConfig));

        double island = islandNoise.sample(x * 0.01, 0, z * 0.01);
        if (island < 0.2) return 0;

        int baseHeight = 90 + (int)(heightNoise.sample(x * 0.02, 0, z * 0.02) * 15);
        int thickness = (int)(island * 25) + 10;

        return MathHelper.clamp(baseHeight + thickness, world.getBottomY(), world.getTopY());
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        setupSamplers(getSeedFromConfig(noiseConfig));

        int height = getHeight(x, z, Heightmap.Type.WORLD_SURFACE, world, noiseConfig);
        BlockState[] states = new BlockState[world.getHeight()];

        // Pre-fill empty block template state array index layers to prevent game crashes
        for (int i = 0; i < states.length; i++) {
            int currentY = world.getBottomY() + i;
            if (currentY <= height && height > 0) {
                states[i] = BlockInit.SolarRock.getDefaultState();
            } else {
                states[i] = Blocks.AIR.getDefaultState();
            }
        }
        return new VerticalBlockSample(world.getBottomY(), states);
    }

    @Override
    public void populateEntities(ChunkRegion region) {}

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {}
}
