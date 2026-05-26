package com.cosmo.datagen;

import com.cosmo.init.BlockInit;
import com.cosmo.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class CosmicVeilModelProvider extends FabricModelProvider {
	public CosmicVeilModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		BlockStateModelGenerator.BlockTexturePool RedShadowRockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.RedShadowRock);
        RedShadowRockPool.wall(BlockInit.RedShadowRockWall);
        RedShadowRockPool.slab(BlockInit.RedShadowRockSlab);
        RedShadowRockPool.stairs(BlockInit.RedShadowRockStair);
        RedShadowRockPool.button(BlockInit.RedShadowRockButton);
        RedShadowRockPool.pressurePlate(BlockInit.RedShadowRockPressurePlate);
        BlockStateModelGenerator.BlockTexturePool LightShadowRockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.LightShadowRock);
        LightShadowRockPool.wall(BlockInit.LightShadowRockWall);
        LightShadowRockPool.slab(BlockInit.LightShadowRockSlab);
        LightShadowRockPool.stairs(BlockInit.LightShadowRockStair);
        LightShadowRockPool.button(BlockInit.LightShadowRockButton);
        LightShadowRockPool.pressurePlate(BlockInit.LightShadowRockPressurePlate);
        BlockStateModelGenerator.BlockTexturePool DarkShadowRockPool = blockStateModelGenerator.registerCubeAllModelTexturePool(BlockInit.DarkShadowRock);
        DarkShadowRockPool.wall(BlockInit.DarkShadowRockWall);
        DarkShadowRockPool.slab(BlockInit.DarkShadowRockSlab);
        DarkShadowRockPool.stairs(BlockInit.DarkShadowRockStair);
        DarkShadowRockPool.button(BlockInit.DarkShadowRockButton);
        DarkShadowRockPool.pressurePlate(BlockInit.DarkShadowRockPressurePlate);
        blockStateModelGenerator.registerSimpleState(BlockInit.DarkPedestal);
        blockStateModelGenerator.registerSimpleState(BlockInit.ShadowCore);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.RedShadrockOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.LightShadrockOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.DarkShadrockOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.RedEclipsiumOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.LightEclipsiumOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.DarkEclipsiumOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.RedAsheniteOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.LightAsheniteOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.DarkAsheniteOre);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.AsheniteBlock);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.ShadrockBlock);
        blockStateModelGenerator.registerSimpleCubeAll(BlockInit.EclipsiumBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemInit.SHADOW_BOOTS,Models.GENERATED);
        itemModelGenerator.register(ItemInit.SHADOW_LEGGINGS,Models.GENERATED);
        itemModelGenerator.register(ItemInit.SHADOW_CHESTPLATE,Models.GENERATED);
        itemModelGenerator.register(ItemInit.SHADOW_HELMET,Models.GENERATED);
        itemModelGenerator.register(ItemInit.ShadowTemplate,Models.GENERATED);
        itemModelGenerator.registerCompass(ItemInit.ShadowCompass);
        itemModelGenerator.register(ItemInit.RawEclipsium, Models.GENERATED);
        itemModelGenerator.register(ItemInit.RawShadrock, Models.GENERATED);
        itemModelGenerator.register(ItemInit.ShadrockNugget, Models.GENERATED);
        itemModelGenerator.register(ItemInit.EclipsiumNugget, Models.GENERATED);
        itemModelGenerator.register(ItemInit.Ashenite, Models.GENERATED);
        itemModelGenerator.register(ItemInit.EclipsiumIngot, Models.GENERATED);
        itemModelGenerator.register(ItemInit.ShadrockIngot, Models.GENERATED);
        itemModelGenerator.register(ItemInit.ShadowMirror, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.ShadowImbuer, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.WeeperPearl, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.ShadowTrap, Models.GENERATED);
        itemModelGenerator.register(ItemInit.WeeperTotem, Models.GENERATED);
        itemModelGenerator.register(ItemInit.ShadowCalibrator, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.WeeperSpawnEgg,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
    }
}