package com.cosmo.world.feature;

import com.cosmo.init.BlockInit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class DarkPillarsFeature extends Feature<DefaultFeatureConfig> {

    public DarkPillarsFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess level = context.getWorld();
        BlockPos origin = context.getOrigin();

        // Step 1: Find floor at center
        BlockPos floorPos = null;
        for (int y = origin.getY(); y > level.getBottomY(); y--) {
            BlockPos pos = new BlockPos(origin.getX(), y, origin.getZ());
            if (!level.getBlockState(pos).isAir() && level.getBlockState(pos.up()).isAir()) {
                floorPos = pos.up();
                break;
            }
        }
        if (floorPos == null) return false;

        // Step 2: Find ceiling at center
        BlockPos ceilingPos = null;
        for (int y = floorPos.getY(); y < level.getTopY(); y++) {
            BlockPos pos = new BlockPos(origin.getX(), y, origin.getZ());
            if (!level.getBlockState(pos).isAir() && level.getBlockState(pos.down()).isAir()) {
                ceilingPos = pos.down();
                break;
            }
        }
        if (ceilingPos == null) return false;
        int pillarHeight = ceilingPos.getY() - floorPos.getY();
        if (pillarHeight < 4) return false;
        int MaxRadius = Random.create().nextBetweenExclusive(3,7);
        int centerY = floorPos.getY() + pillarHeight / 2;

        for (int y = floorPos.getY()-2; y <= ceilingPos.getY()+2; y++) {
                float distanceFromCenter = Math.abs(y - centerY);
                int radius = (int) (MaxRadius*Math.pow(distanceFromCenter/((double) pillarHeight /2),2))+1;
            placeCircle(level,new BlockPos(origin.getX(),y,origin.getZ()),radius);
        }
        return true;
    }


    private void placeCircle(StructureWorldAccess level, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx * dx + dz * dz <= radius * radius) {
                    BlockPos pos = center.add(dx, 0, dz);
                    if (level.getBlockState(pos).isAir() || level.getBlockState(pos).isReplaceable()) {
                        level.setBlockState(pos, BlockInit.DarkShadowRock.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}
