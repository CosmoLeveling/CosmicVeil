package com.cosmo.world.generator;

import com.cosmo.init.BlockInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.Random;
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
    ).apply(instance,ShadowRealmChunkGenerator::new));
    private final int worldHeight;
    private final PerlinNoiseSampler heightNoise;
    private final PerlinNoiseSampler floorNoise;
    private final Random random;
    public ShadowRealmChunkGenerator(BiomeSource biomeSource, int worldHeight) {
        super(biomeSource);
        this.worldHeight = worldHeight;
        long seed = 12345L;
        random = Random.create(seed);
        this.heightNoise = new PerlinNoiseSampler(random);
        Random random_floor = Random.create(seed);
        long seedCave = 98765L;
        Random randomCave = Random.create(seedCave);
        Random randomLargeCave = Random.create(seedCave);
        this.floorNoise = new PerlinNoiseSampler(random_floor);
        new PerlinNoiseSampler(randomCave);
        new PerlinNoiseSampler(randomLargeCave);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver carverStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {

    }

    @Override
    public CompletableFuture<Chunk> populateBiomes(Executor executor, NoiseConfig noiseConfig, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return super.populateBiomes(executor, noiseConfig, blender, structureAccessor, chunk);
    }

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    @Override
    public int getWorldHeight() {
        return this.worldHeight;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        BlockPos.Mutable blockPos = new BlockPos.Mutable();

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

                for (int y = 80+surfaceY; y > 70 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ);
                }

                for (int y = 45+surfaceY; y > 40 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ);
                }

                for (int y = 10+surfaceY; y > 5 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ);
                }

                for (int y = -25+surfaceY; y > -30 + surfaceY; y--) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ);
                }

                for (int y = floorY; y < floorY + (int)(floorNoiseVal * 3) + 10; y++) {
                    blockPos.set(worldX, y, worldZ);
                    placeNoiseBlocks(chunk, blockPos, worldX, worldZ);
                }
                chunk.setBlockState(new BlockPos(worldX,floorY,worldZ),Blocks.BEDROCK.getDefaultState(), false);
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return -65;
    }

    private void placeNoiseBlocks(Chunk chunk, BlockPos blockPos, float worldX, float worldZ) {
        double floor_noiseScale = 0.05;
        double floor_noiseValue = floorNoise.sample(worldX * floor_noiseScale, blockPos.getY() * floor_noiseScale, worldZ * floor_noiseScale);
        double floor_noise_val = floor_noiseValue + ((double) random.nextBetween(-1, 1)/10);
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
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        double heightNoiseVal = heightNoise.sample(x * 0.03, 0, z * 0.03);
        int surfaceY = (int)(heightNoiseVal * 5);
        return 80+surfaceY;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return null;
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {

    }
}
