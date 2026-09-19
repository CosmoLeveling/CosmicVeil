package com.cosmo.world.feature;

import com.cosmo.CosmicVeil;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SolarMonumentStructureFeature extends Feature<DefaultFeatureConfig> {

    private static final BlockPos START_BLOCK = new BlockPos(8, 3, 8);
    private static final ChunkPos START_CHUNK = new ChunkPos(START_BLOCK);
    private static final int MAX_RADIUS = 16;
    private static final int field_31521 = 1;

    public SolarMonumentStructureFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    private static int getDistance(int x1, int z1, int x2, int z2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(z1 - z2));
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess level = context.getWorld();
        Random random = context.getRandom();
        ChunkPos chunkPos = new ChunkPos(context.getOrigin());
        
        // Check if the chunk is within a reasonable initial radius (Original logic retained)
        if (getDistance(chunkPos.x, chunkPos.z, START_CHUNK.x, START_CHUNK.z) > 1) {
            return true;
        } else {
            BlockPos blockPos = START_BLOCK.withY(context.getOrigin().getY() + START_BLOCK.getY());
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            if (level.getServer()!=null) {
                // FIX APPLIED HERE: Restrict placement check to exactly chunk (0, 0) and ensure target coordinates (0, 100, 0) are used.
                if (chunkPos.x == 0 && chunkPos.z == 0) {
                    StructureTemplateManager manager = level.getServer().getStructureTemplateManager();
                    var templateOpt = manager.getTemplate(new Identifier(CosmicVeil.MOD_ID,"solar_monument"));
                    if (templateOpt.isEmpty()) return false;
                    StructureTemplate structure = templateOpt.get();
                    StructurePlacementData placement = new StructurePlacementData();
                    // Place the monument using the desired top-left corner (0, 100, 0)
                    structure.place(level,new BlockPos(0,100,0),new BlockPos(0,100,0),placement,random,2);
                }
                // Keep the original return true if the initial checks passed but placement didn't happen.
                return true;
            }
            return true;
        }
    }
}