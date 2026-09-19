package com.cosmo.world.generator;

import com.cosmo.init.BlockInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.LocalRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSplitter;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
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

public class ShadowRealmChunkGenerator extends ChunkGenerator {

    public static final Codec<ShadowRealmChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(ShadowRealmChunkGenerator::getBiomeSource),
            Codec.INT.fieldOf("world_height").forGetter(ShadowRealmChunkGenerator::getWorldHeight)
    ).apply(instance, ShadowRealmChunkGenerator::new));

    private final int worldHeight;
    private PerlinNoiseSampler heightNoise;
    private PerlinNoiseSampler floorNoise;
    private long initializedSeed = Long.MIN_VALUE;

    public ShadowRealmChunkGenerator(BiomeSource biomeSource, int worldHeight) {
        super(biomeSource);
        this.worldHeight = worldHeight;
        setupSamplers(12345L);
    }

    private synchronized void setupSamplers(long seed) {
        if (this.initializedSeed == seed) return;
        this.initializedSeed = seed;
        this.heightNoise = new PerlinNoiseSampler(Random.create(seed));
        this.floorNoise = new PerlinNoiseSampler(Random.create(seed + 1));
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {}

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {}

    @Override
    public CompletableFuture<Chunk> populateBiomes(Executor executor, NoiseConfig noiseConfig, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return super.populateBiomes(executor, noiseConfig, blender, structureAccessor, chunk);
    }

    @Override
    public void populateEntities(ChunkRegion region) {}

    @Override
    public int getWorldHeight() {
        return this.worldHeight;
    }
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
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

        // 1. Thread-safe seed generation workaround via native RandomSplitter
        RandomSplitter splitter = noiseConfig.getAquiferRandomDeriver();
        Random threadSafeRandom = splitter.split(chunkPos.x, 0, chunkPos.z);

        // Form an explicit unique runtime long value from coordinates to bind noise configurations
        setupSamplers(getSeedFromConfig(noiseConfig));

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkPos.getStartX() + x;
                int worldZ = chunkPos.getStartZ() + z;

                // --- Surface generation ---
                double heightNoiseVal = heightNoise.sample(worldX * 0.03, 0, worldZ * 0.03);
                int surfaceY = (int)(heightNoiseVal * 5); // around Y=80

                // --- Floor generation ---
                double floorNoiseVal = floorNoise.sample(worldX * 0.02, 0, worldZ * 0.02);
                int floorY = chunk.getBottomY();

                // Layer 1
                for (int y = 80 + surfaceY; y > 70 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ, threadSafeRandom);
                }

                // Layer 2
                for (int y = 45 + surfaceY; y > 40 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ, threadSafeRandom);
                }

                // Layer 3
                for (int y = 10 + surfaceY; y > 5 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ, threadSafeRandom);
                }

                // Layer 4
                for (int y = -25 + surfaceY; y > -30 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ, threadSafeRandom);
                }

                // Core Bottom Crust
                for (int y = floorY; y < floorY + (int)(floorNoiseVal * 3) + 10; y++) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ, threadSafeRandom);
                }

                // Solid Bedrock Floor Base Pass
                blockPos.set(worldX, floorY, worldZ);
                chunk.setBlockState(blockPos, Blocks.BEDROCK.getDefaultState(), false);
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return -65;
    }

    // FIXED: Now takes an explicit ThreadSafe Random parameter to prevent parallel task blocking
    private void placeNoiseBlocks(Chunk chunk, BlockPos blockPos, float worldX, float worldZ, Random chunkRandom) {
        double floor_noiseScale = 0.05;
        double floor_noiseValue = floorNoise.sample(worldX * floor_noiseScale, blockPos.getY() * floor_noiseScale, worldZ * floor_noiseScale);

        // FIXED: Safe local execution scope random processing
        double floor_noise_val = floor_noiseValue + ((double) chunkRandom.nextBetween(-1, 1) / 10.0);

        if (floor_noise_val > 0) {
            chunk.setBlockState(blockPos, BlockInit.RedShadowRock.getDefaultState(), false);
        } else {
            if (floor_noise_val > -0.2) {
                chunk.setBlockState(blockPos, BlockInit.DarkShadowRock.getDefaultState(), false);
            } else {
                chunk.setBlockState(blockPos, BlockInit.LightShadowRock.getDefaultState(), false);
            }
        }
    }

    @Override
    public int getMinimumY() {
        // FIXED: Changed from 0 to -64 because your generator samples into chunk.getBottomY()
        return -64;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        long runtimeSeed = (long) x * 341873128712L ^ (long) z * 132897987541L;
        setupSamplers(runtimeSeed);

        double heightNoiseVal = heightNoise.sample(x * 0.03, 0, z * 0.03);
        int surfaceY = (int)(heightNoiseVal * 5);
        return 80 + surfaceY;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        int height = getHeight(x, z, Heightmap.Type.WORLD_SURFACE, world, noiseConfig);
        BlockState[] states = new BlockState[world.getHeight()];

        // FIXED: Filled states array to satisfy core lightning engine and entity requirements safely
        for (int i = 0; i < states.length; i++) {
            int currentY = world.getBottomY() + i;
            if (currentY <= height) {
                states[i] = BlockInit.DarkShadowRock.getDefaultState();
            } else {
                states[i] = Blocks.AIR.getDefaultState();
            }
        }
        return new VerticalBlockSample(world.getBottomY(), states);
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {}
}
