package com.cosmo.world.feature;

import com.cosmo.CosmicVeil;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class ShadowPortalStructureFeature extends Feature<DefaultFeatureConfig> {

    public ShadowPortalStructureFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess level = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

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
        if (level.getServer()!=null) {
            StructureTemplateManager manager = level.getServer().getStructureTemplateManager();
            var templateOpt = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"shadow_portal"));
            if (templateOpt.isEmpty()) return false;
            StructureTemplate structure = templateOpt.get();
            StructurePlacementData placement = new StructurePlacementData();
            placement.setRotation(BlockRotation.random(random));
            structure.place(level,floorPos,floorPos,placement,random,2);
        }
        return true;
    }
}
