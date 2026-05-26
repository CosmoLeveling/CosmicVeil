package com.cosmo.world.feature;

import com.cosmo.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;

public class DarkSpireFeature extends Feature<DefaultFeatureConfig> {

    private final List<List<Block>> blocklist = List.of(
            List.of(BlockInit.DarkShadowRock,BlockInit.DarkShadowRockLight),
            List.of(BlockInit.RedShadowRock,BlockInit.RedShadowRockLight),
            List.of(BlockInit.LightShadowRock,BlockInit.LightShadowRockLight)
    );
    
    public DarkSpireFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        List<Block> blockList = blocklist.get(Random.create().nextBetween(0,blocklist.size()-1));
        Block mainBlock = blockList.get(0);
        Block lightBlock = blockList.get(1);
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
            placeCircle(level,mainBlock, floorPos.up(y), radius);
        }

        // === Step 4: Build emerald-shaped crystal top ===
        // (a diamond-like octahedron shape)
        BlockPos gemBase = topPos.up(3);

        for (int i = 0; i < 9; i++) {
            level.setBlockState(gemBase.add(0,i,0),mainBlock.getDefaultState(),2);
            if (i >= 1&&i<=3) {
                level.setBlockState(gemBase.add(1,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-1,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,1),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,-1),lightBlock.getDefaultState(),2);
            }
            if (i >= 3&&i<=5) {
                level.setBlockState(gemBase.add(2,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-2,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,2),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,-2),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(1,i,1),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-1,i,1),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(1,i,-1),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-1,i,-1),mainBlock.getDefaultState(),2);
            }
            if (i == 4) {
                level.setBlockState(gemBase.add(1,i,0),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-1,i,0),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,1),mainBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,-1),mainBlock.getDefaultState(),2);
            }
            if (i >= 5&&i<=7) {
                level.setBlockState(gemBase.add(1,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(-1,i,0),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,1),lightBlock.getDefaultState(),2);
                level.setBlockState(gemBase.add(0,i,-1),lightBlock.getDefaultState(),2);
            }
        }

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
}
