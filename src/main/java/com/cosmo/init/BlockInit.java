package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.blocks.DarkPillarBlock;
import com.cosmo.blocks.ShadowCoreBlock;
import com.cosmo.blocks.ShadowPortal;
import com.cosmo.blocks.ShadowTransporter;
import com.cosmo.items.CoreBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockInit {

    public static final BlockSetType RedShadowRockType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(new Identifier(CosmicVeil.MOD_ID,"red_shadow_rock"));
    public static final BlockSetType DarkShadowRockType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(new Identifier(CosmicVeil.MOD_ID,"dark_shadow_rock"));
    public static final BlockSetType LightShadowRockType = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).build(new Identifier(CosmicVeil.MOD_ID,"light_shadow_rock"));
    public static final Block ShadowTransporter = registerBlock("shadow_transporter",
            new ShadowTransporter(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block ShadowPortal = registerBlock("shadow_portal",
            new ShadowPortal(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block DarkShadowRockLight = registerBlock("dark_shadow_rock_light",
            new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block DarkShadowRock = registerBlock("dark_shadow_rock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkShadowRockWall = registerBlock("dark_shadow_rock_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkShadowRockStair = registerBlock("dark_shadow_rock_stairs",
            new StairsBlock(DarkShadowRock.getDefaultState(),FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkShadowRockSlab = registerBlock("dark_shadow_rock_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkShadowRockPressurePlate = registerBlock("dark_shadow_rock_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),DarkShadowRockType));
    public static final Block DarkShadowRockButton = registerBlock("dark_shadow_rock_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),DarkShadowRockType,10,false));
    public static final Block DarkShadrockOre = registerBlock("dark_shadrock_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkEclipsiumOre = registerBlock("dark_eclipsium_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkAsheniteOre = registerBlock("dark_ashenite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block DarkPedestal = registerBlock("dark_pillar",
            new DarkPillarBlock(FabricBlockSettings.copyOf(DarkShadowRock).nonOpaque()));
    public static final Block ShadowCore = registerBlockWithoutItem("shadow_core",
            new ShadowCoreBlock(FabricBlockSettings.copyOf(DarkShadowRock).nonOpaque()));
    public static final Block LightShadowRockLight = registerBlock("light_shadow_rock_light",
            new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block LightShadowRock = registerBlock("light_shadow_rock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightShadowRockWall = registerBlock("light_shadow_rock_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightShadowRockStair = registerBlock("light_shadow_rock_stairs",
            new StairsBlock(LightShadowRock.getDefaultState(),FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightShadowRockSlab = registerBlock("light_shadow_rock_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightShadowRockPressurePlate = registerBlock("light_shadow_rock_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),LightShadowRockType));
    public static final Block LightShadowRockButton = registerBlock("light_shadow_rock_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),LightShadowRockType,10,false));
    public static final Block LightShadrockOre = registerBlock("light_shadrock_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightEclipsiumOre = registerBlock("light_eclipsium_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block LightAsheniteOre = registerBlock("light_ashenite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedShadowRockLight = registerBlock("red_shadow_rock_light",
            new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));
    public static final Block RedShadowRock = registerBlock("red_shadow_rock",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedShadowRockStair = registerBlock("red_shadow_rock_stairs",
            new StairsBlock(RedShadowRock.getDefaultState(),FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedShadowRockWall = registerBlock("red_shadow_rock_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedShadowRockSlab = registerBlock("red_shadow_rock_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedShadowRockPressurePlate = registerBlock("red_shadow_rock_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),RedShadowRockType));
    public static final Block RedShadowRockButton = registerBlock("red_shadow_rock_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER)),RedShadowRockType,10,false));
    public static final Block RedShadrockOre = registerBlock("red_shadrock_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedEclipsiumOre = registerBlock("red_eclipsium_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));
    public static final Block RedAsheniteOre = registerBlock("red_ashenite_ore",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).allowsSpawning(((state, world, pos, type) -> type == EntityInit.WEEPER))));

    public static final Block AsheniteBlock = registerBlock("ashenite_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)));
    public static final Block ShadrockBlock = registerBlock("shadrock_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block EclipsiumBlock = registerBlock("eclipsium_block",
            new Block(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK)));

    public static Block registerBlockWithoutItem(String name, Block block){
        return Registry.register(Registries.BLOCK,new Identifier(CosmicVeil.MOD_ID,name),block);
    }
    public static Block registerBlock(String name, Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(CosmicVeil.MOD_ID,name),block);
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(CosmicVeil.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void init(){
        Registry.register(Registries.ITEM,new Identifier(CosmicVeil.MOD_ID,"shadow_core"),new CoreBlockItem(ShadowCore,new FabricItemSettings()));
    }
}
