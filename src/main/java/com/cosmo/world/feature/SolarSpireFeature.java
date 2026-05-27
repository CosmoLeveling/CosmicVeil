package com.cosmo.world.feature;

import com.cosmo.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class SolarSpireFeature extends Feature<DefaultFeatureConfig> {

    public SolarSpireFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess level = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();
        // === Step 1: Find floor ===
        BlockPos floorPos = null;
        for (int y = origin.getY(); y > level.getBottomY(); y--) {
            BlockPos pos = new BlockPos(origin.getX(), y, origin.getZ());
            if (!level.getBlockState(pos).isAir() && level.getBlockState(pos.up()).isAir()) {
                floorPos = pos.up();
                break;
            }
        }
        if (floorPos == null) return false;

        // === Step 2: Randomize spire height ===
        int spireHeight = 10 + random.nextInt(5); // 15–35 blocks tall
        BlockPos topPos = floorPos.up(spireHeight);

        // === Step 3: Build spire body ===
        for (int y = 0; y < spireHeight; y++) {
            double progress = (double) y / spireHeight;
            // Spire tapers exponentially toward the top
            int radius = Math.max(1, (int)(3 * Math.pow(1.0 - progress, 2.0)));
            placeCircle(level, BlockInit.LightSolarRock, floorPos.up(y), radius);
        }
        for (int y = spireHeight;y<spireHeight+5;y++){
            level.setBlockState(floorPos.up(y),BlockInit.LightSolarRock.getDefaultState(), 2);
        }

        // === Step 4: Build emerald-shaped crystal top ===
        // (a diamond-like octahedron shape)
        BlockPos gemBase = topPos.up(3);
        placeHollowCircle(level, BlockInit.LightSolarRock, gemBase.up(0), 8);
        placeHollowCircle(level, BlockInit.LightSolarRock, gemBase.up(2), 9);
        placeHollowCircle(level, BlockInit.LightSolarRock, gemBase.up(4), 8);
        placeHollowCircle(level, BlockInit.LightSolarRock, gemBase.up(6), 7);
        return true;
    }



    private void placeCircle(StructureWorldAccess level,Block block, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius) {
                    BlockPos pos = center.add(dx, 0, dz);
                    if (level.getBlockState(pos).isAir() || level.getBlockState(pos).isReplaceable()) {
                        level.setBlockState(pos, block.getDefaultState(), 2);
                    }
                }
            }
        }
    }
    private void placeHollowCircle(StructureWorldAccess level, Block block, BlockPos center, int radius) {
        int x = radius;
        int z = 0;
        int decisionVariable = 1 - x;

        // Draw the initial points on the axes
        drawSymmetricPoints(level, block, center, x, z);

        while (x > z) {
            z++;
            if (decisionVariable < 0) {
                decisionVariable += 2 * z + 1;
            } else {
                x--;
                decisionVariable += 2 * (z - x) + 1;
            }
            drawSymmetricPoints(level, block, center, x, z);
        }
    }

    private void drawSymmetricPoints(StructureWorldAccess level, Block block, BlockPos center, int x, int z) {
        // Generate the 8 symmetric offsets around the center
        BlockPos[] points = new BlockPos[] {
                center.add(x, 0, z),
                center.add(-x, 0, z),
                center.add(x, 0, -z),
                center.add(-x, 0, -z),
                center.add(z, 0, x),
                center.add(-z, 0, x),
                center.add(z, 0, -x),
                center.add(-z, 0, -x)
        };

        for (BlockPos pos : points) {
            if (level.getBlockState(pos).isAir() || level.getBlockState(pos).isReplaceable()) {
                level.setBlockState(pos, block.getDefaultState(), 2);
            }
        }
    }
}
