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

public class ShadowMonarchsTreasureStructureFeature extends Feature<DefaultFeatureConfig> {

    public ShadowMonarchsTreasureStructureFeature() {
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
            var templateOpt = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"shadow_monarchs_treasure"));
            var templateOpt2 = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"shadow_altar_template"));
            var templateOpt3 = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"shadow_altar_mirror"));
            var templateOpt4 = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"shadow_monarchs_ruin"));
            if (templateOpt.isEmpty()||templateOpt2.isEmpty()||templateOpt3.isEmpty()||templateOpt4.isEmpty()) return false;
            StructureTemplate structure = templateOpt.get();
            int rand = random.nextBetween(0,2);
            if (rand == 0){
                structure = templateOpt2.get();
            } else if (rand == 1){
                structure = templateOpt3.get();
            } else if (random.nextBetween(0,9) == 0) {
                structure = templateOpt4.get();
            }

            StructurePlacementData placement = new StructurePlacementData();
            placement.setRotation(BlockRotation.random(random));
            structure.place(level,floorPos,floorPos,placement,random,2);
        }
        return true;
    }
}
