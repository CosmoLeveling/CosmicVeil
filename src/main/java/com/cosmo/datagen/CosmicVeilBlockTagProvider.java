package com.cosmo.datagen;

import com.cosmo.init.BlockInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class CosmicVeilBlockTagProvider extends FabricTagProvider<Block> {

    public CosmicVeilBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.ANIMALS_SPAWNABLE_ON)
                .add(BlockInit.DarkShadowRock)
                .add(BlockInit.LightSolarRock)
                .add(BlockInit.SolarRock)
                .add(BlockInit.DarkSolarRock)
                .add(BlockInit.LightShadowRock)
                .add(BlockInit.RedShadowRock);
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(BlockInit.ShadowTransporter)
                .add(BlockInit.ShadowPortal)
                .add(BlockInit.DarkShadowRockLight)
                .add(BlockInit.RedShadowRockLight)
                .add(BlockInit.LightShadowRockLight)
                .add(BlockInit.DarkPedestal)
                .add(BlockInit.ShadowCore)
                .add(BlockInit.DarkShadowRock)
                .add(BlockInit.DarkShadowRockStair)
                .add(BlockInit.DarkShadowRockSlab)
                .add(BlockInit.DarkShadowRockWall)
                .add(BlockInit.DarkShadowRockButton)
                .add(BlockInit.DarkShadowRockPressurePlate)

                .add(BlockInit.LightShadowRock)
                .add(BlockInit.LightShadowRockStair)
                .add(BlockInit.LightShadowRockSlab)
                .add(BlockInit.LightShadowRockWall)
                .add(BlockInit.LightShadowRockButton)
                .add(BlockInit.LightShadowRockPressurePlate)

                .add(BlockInit.RedShadowRock)
                .add(BlockInit.RedShadowRockStair)
                .add(BlockInit.RedShadowRockSlab)
                .add(BlockInit.RedShadowRockWall)
                .add(BlockInit.RedShadowRockButton)
                .add(BlockInit.RedShadowRockPressurePlate)
                .add(BlockInit.RedShadrockOre)
                .add(BlockInit.RedEclipsiumOre)
                .add(BlockInit.RedAsheniteOre)
                .add(BlockInit.LightShadrockOre)
                .add(BlockInit.LightEclipsiumOre)
                .add(BlockInit.LightAsheniteOre)
                .add(BlockInit.DarkShadrockOre)
                .add(BlockInit.DarkEclipsiumOre)
                .add(BlockInit.DarkAsheniteOre)
                ;
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(BlockInit.ShadowTransporter)
                .add(BlockInit.ShadowPortal)
                .add(BlockInit.DarkShadowRock)
                .add(BlockInit.DarkShadowRockStair)
                .add(BlockInit.DarkShadowRockSlab)
                .add(BlockInit.DarkShadowRockWall)
                .add(BlockInit.DarkShadowRockButton)
                .add(BlockInit.DarkShadowRockPressurePlate)

                .add(BlockInit.LightShadowRock)
                .add(BlockInit.LightShadowRockStair)
                .add(BlockInit.LightShadowRockSlab)
                .add(BlockInit.LightShadowRockWall)
                .add(BlockInit.LightShadowRockButton)
                .add(BlockInit.LightShadowRockPressurePlate)

                .add(BlockInit.DarkPedestal)
                .add(BlockInit.ShadowCore)
                .add(BlockInit.RedShadowRock)
                .add(BlockInit.RedShadowRockStair)
                .add(BlockInit.RedShadowRockSlab)
                .add(BlockInit.RedShadowRockWall)
                .add(BlockInit.RedShadowRockButton)
                .add(BlockInit.RedShadowRockPressurePlate)
        ;

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(BlockInit.RedShadrockOre)
                .add(BlockInit.RedEclipsiumOre)
                .add(BlockInit.RedAsheniteOre)
                .add(BlockInit.LightShadrockOre)
                .add(BlockInit.LightEclipsiumOre)
                .add(BlockInit.LightAsheniteOre)
                .add(BlockInit.DarkShadrockOre)
                .add(BlockInit.DarkEclipsiumOre)
                .add(BlockInit.DarkAsheniteOre)
                .add(BlockInit.ShadrockBlock)
                .add(BlockInit.EclipsiumBlock)
                .add(BlockInit.AsheniteBlock)
                ;

        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(BlockInit.RedShadowRockWall)
                .add(BlockInit.LightShadowRockWall)
                .add(BlockInit.DarkShadowRockWall);
    }
}
