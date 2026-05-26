package com.cosmo.datagen;

import com.cosmo.init.BlockInit;
import com.cosmo.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class CosmicVeilRecipeProvider extends FabricRecipeProvider {
    public CosmicVeilRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.WeeperTotem)
                .pattern("iWi")
                .pattern("NDN")
                .pattern(" N ")
                .input('W', ItemInit.WeeperPearl)
                .input('i', ItemInit.ShadrockNugget)
                .input('N', ItemInit.EclipsiumIngot)
                .input('D', ItemInit.Ashenite)
                .criterion(hasItem(Items.NETHERITE_INGOT),conditionsFromItem(Items.NETHERITE_INGOT))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.WeeperTotem)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.ShadowTrap,8)
                .pattern("iDi")
                .pattern("DWD")
                .pattern("iDi")
                .input('W', ItemInit.WeeperPearl)
                .input('i', ItemInit.ShadrockNugget)
                .input('D', ItemInit.Ashenite)
                .criterion(hasItem(ItemInit.WeeperPearl),conditionsFromItem(ItemInit.WeeperPearl))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.ShadowTrap)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, BlockInit.ShadowTransporter)
                .pattern("iWi")
                .pattern("WDW")
                .pattern("iIi")
                .input('W',ItemInit.WeeperPearl)
                .input('i',ItemInit.ShadrockNugget)
                .input('I',BlockInit.ShadrockBlock)
                .input('D',ItemInit.Ashenite)
                .criterion(hasItem(ItemInit.WeeperPearl),conditionsFromItem(ItemInit.WeeperPearl))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.ShadowTransporter)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.TRANSPORTATION, BlockInit.ShadowPortal)
                .pattern("OEO")
                .pattern("CDC")
                .pattern("IOI")
                .input('E',Items.ENDER_EYE)
                .input('C',Items.CRYING_OBSIDIAN)
                .input('D',Items.DIAMOND)
                .input('I',Items.IRON_BLOCK)
                .input('O',Items.OBSIDIAN)
                .criterion(hasItem(Items.ENDER_EYE),conditionsFromItem(Items.ENDER_EYE))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.ShadowPortal)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.ShadowCalibrator)
                .pattern(" sa")
                .pattern(" Ws")
                .pattern("s  ")
                .input('W',ItemInit.WeeperPearl)
                .input('a',Items.AMETHYST_SHARD)
                .input('s',Items.STICK)
                .criterion(hasItem(ItemInit.WeeperPearl),conditionsFromItem(ItemInit.WeeperPearl))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.ShadowCalibrator)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.MarksmanVeil)
                .pattern(" g ")
                .pattern("dIi")
                .pattern("Wn ")
                .input('g',Items.GUNPOWDER)
                .input('d',ItemInit.Ashenite)
                .input('I',BlockInit.ShadrockBlock)
                .input('i',ItemInit.ShadrockIngot)
                .input('W',ItemInit.WeeperPearl)
                .input('n',ItemInit.ShadrockNugget)
                .criterion(hasItem(ItemInit.Ashenite),conditionsFromItem(ItemInit.Ashenite))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.MarksmanVeil)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.ShadowShield)
                .pattern("iSi")
                .pattern("dsd")
                .pattern(" W ")
                .input('s',Items.SHIELD)
                .input('S',ItemInit.ShadowTrap)
                .input('d',ItemInit.Ashenite)
                .input('i',ItemInit.ShadrockNugget)
                .input('W',ItemInit.WeeperPearl)
                .criterion(hasItem(ItemInit.WeeperPearl),conditionsFromItem(ItemInit.WeeperPearl))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.ShadowShield)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockInit.DarkShadowRockLight,16)
                .pattern("DDD")
                .pattern("DGD")
                .pattern("DDD")
                .input('D',BlockInit.DarkShadowRock)
                .input('G', Blocks.GLOWSTONE)
                .criterion(hasItem(Items.GLOWSTONE),conditionsFromItem(Items.GLOWSTONE))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.DarkShadowRockLight)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockInit.LightShadowRockLight,16)
                .pattern("DDD")
                .pattern("DGD")
                .pattern("DDD")
                .input('D',BlockInit.LightShadowRock)
                .input('G', Blocks.GLOWSTONE)
                .criterion(hasItem(Items.GLOWSTONE),conditionsFromItem(Items.GLOWSTONE))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.LightShadowRockLight)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockInit.RedShadowRockLight,16)
                .pattern("DDD")
                .pattern("DGD")
                .pattern("DDD")
                .input('D',BlockInit.RedShadowRock)
                .input('G', Blocks.GLOWSTONE)
                .criterion(hasItem(Items.GLOWSTONE),conditionsFromItem(Items.GLOWSTONE))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.RedShadowRockLight)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ItemInit.ShadowCompass)
                .pattern(" W ")
                .pattern("iCi")
                .pattern(" i ")
                .input('W',ItemInit.WeeperPearl)
                .input('i', ItemInit.ShadrockNugget)
                .input('C', Items.COMPASS)
                .criterion(hasItem(Items.COMPASS),conditionsFromItem(Items.COMPASS))
                .offerTo(consumer,new Identifier(getRecipeName(ItemInit.ShadowCompass)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockInit.ShadowCore)
                .pattern("S S")
                .pattern("AWA")
                .pattern("DAD")
                .input('W',ItemInit.WeeperPearl)
                .input('S', ItemInit.ShadrockNugget)
                .input('D',BlockInit.DarkShadowRock)
                .input('A', ItemInit.Ashenite)
                .criterion(hasItem(ItemInit.Ashenite),conditionsFromItem(ItemInit.Ashenite))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.ShadowCore)));
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, BlockInit.DarkPedestal)
                .pattern("D D")
                .pattern("SLS")
                .pattern("lll")
                .input('D',BlockInit.DarkShadowRock)
                .input('L',BlockInit.LightShadowRock)
                .input('S',ItemInit.ShadrockNugget)
                .input('l', BlockInit.LightShadowRockSlab)
                .criterion(hasItem(ItemInit.Ashenite),conditionsFromItem(ItemInit.Ashenite))
                .offerTo(consumer,new Identifier(getRecipeName(BlockInit.DarkPedestal)));

        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.DarkShadowRockStair,BlockInit.DarkShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.DarkShadowRockWall,BlockInit.DarkShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.DarkShadowRockSlab,BlockInit.DarkShadowRock,2);
        createStairsRecipe(BlockInit.DarkShadowRockStair, Ingredient.ofItems(BlockInit.DarkShadowRock))
            .criterion(hasItem(BlockInit.DarkShadowRock),conditionsFromItem(BlockInit.DarkShadowRock))
            .offerTo(consumer, new Identifier(getRecipeName(BlockInit.DarkShadowRockStair)));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,BlockInit.DarkShadowRockSlab, Ingredient.ofItems(BlockInit.DarkShadowRock))
                .criterion(hasItem(BlockInit.DarkShadowRock),conditionsFromItem(BlockInit.DarkShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.DarkShadowRockSlab)));
        createPressurePlateRecipe(RecipeCategory.REDSTONE,BlockInit.DarkShadowRockPressurePlate, Ingredient.ofItems(BlockInit.DarkShadowRock))
                .criterion(hasItem(BlockInit.DarkShadowRock),conditionsFromItem(BlockInit.DarkShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.DarkShadowRockPressurePlate)));
        offerWallRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.DarkShadowRockWall, BlockInit.DarkShadowRock);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE,BlockInit.DarkShadowRockButton)
                .input(BlockInit.DarkShadowRock)
                .criterion(hasItem(BlockInit.DarkShadowRock),conditionsFromItem(BlockInit.DarkShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.DarkShadowRockButton)));

        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.LightShadowRockStair,BlockInit.LightShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.LightShadowRockWall,BlockInit.LightShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.LightShadowRockSlab,BlockInit.LightShadowRock,2);
        createStairsRecipe(BlockInit.LightShadowRockStair, Ingredient.ofItems(BlockInit.LightShadowRock))
                .criterion(hasItem(BlockInit.LightShadowRock),conditionsFromItem(BlockInit.LightShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.LightShadowRockStair)));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,BlockInit.LightShadowRockSlab, Ingredient.ofItems(BlockInit.LightShadowRock))
                .criterion(hasItem(BlockInit.LightShadowRock),conditionsFromItem(BlockInit.LightShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.LightShadowRockSlab)));
        createPressurePlateRecipe(RecipeCategory.REDSTONE,BlockInit.LightShadowRockPressurePlate, Ingredient.ofItems(BlockInit.LightShadowRock))
                .criterion(hasItem(BlockInit.LightShadowRock),conditionsFromItem(BlockInit.LightShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.LightShadowRockPressurePlate)));
        offerWallRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.LightShadowRockWall, BlockInit.LightShadowRock);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE,BlockInit.LightShadowRockButton)
                .input(BlockInit.LightShadowRock)
                .criterion(hasItem(BlockInit.LightShadowRock),conditionsFromItem(BlockInit.LightShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.LightShadowRockButton)));


        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.RedShadowRockStair,BlockInit.RedShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.RedShadowRockWall,BlockInit.RedShadowRock);
        offerStonecuttingRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.RedShadowRockSlab,BlockInit.RedShadowRock,2);
        createStairsRecipe(BlockInit.RedShadowRockStair, Ingredient.ofItems(BlockInit.RedShadowRock))
                .criterion(hasItem(BlockInit.RedShadowRock),conditionsFromItem(BlockInit.RedShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.RedShadowRockStair)));
        createSlabRecipe(RecipeCategory.BUILDING_BLOCKS,BlockInit.RedShadowRockSlab, Ingredient.ofItems(BlockInit.RedShadowRock))
                .criterion(hasItem(BlockInit.RedShadowRock),conditionsFromItem(BlockInit.RedShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.RedShadowRockSlab)));
        createPressurePlateRecipe(RecipeCategory.REDSTONE,BlockInit.RedShadowRockPressurePlate, Ingredient.ofItems(BlockInit.RedShadowRock))
                .criterion(hasItem(BlockInit.RedShadowRock),conditionsFromItem(BlockInit.RedShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.RedShadowRockPressurePlate)));
        offerWallRecipe(consumer,RecipeCategory.BUILDING_BLOCKS,BlockInit.RedShadowRockWall, BlockInit.RedShadowRock);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE,BlockInit.RedShadowRockButton)
                .input(BlockInit.RedShadowRock)
                .criterion(hasItem(BlockInit.RedShadowRock),conditionsFromItem(BlockInit.RedShadowRock))
                .offerTo(consumer, new Identifier(getRecipeName(BlockInit.RedShadowRockButton)));
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer,RecipeCategory.MISC,ItemInit.ShadrockIngot,RecipeCategory.MISC,BlockInit.ShadrockBlock,"shadrock_ingot_from_block","shadrock_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer,RecipeCategory.MISC,ItemInit.ShadrockNugget,RecipeCategory.MISC,ItemInit.ShadrockIngot,"shadrock_nuggets_from_ingots","shadrock_ingot");
        offerReversibleCompactingRecipes(consumer,RecipeCategory.MISC,ItemInit.Ashenite,RecipeCategory.MISC,BlockInit.AsheniteBlock);
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer,RecipeCategory.MISC,ItemInit.EclipsiumIngot,RecipeCategory.MISC,BlockInit.EclipsiumBlock,"eclipsium_ingot_from_block","eclipsium_ingot");
        offerReversibleCompactingRecipesWithReverseRecipeGroup(consumer,RecipeCategory.MISC,ItemInit.EclipsiumNugget,RecipeCategory.MISC,ItemInit.EclipsiumIngot,"eclipsium_nuggets_from_ingot","eclipsium_ingot");

        offerBlasting(consumer, List.of(ItemInit.RawEclipsium,BlockInit.DarkEclipsiumOre,BlockInit.LightEclipsiumOre,BlockInit.RedEclipsiumOre),RecipeCategory.MISC,ItemInit.EclipsiumIngot,0.7f,100,"eclipsium");
        offerBlasting(consumer, List.of(ItemInit.RawShadrock,BlockInit.DarkShadrockOre,BlockInit.LightShadrockOre,BlockInit.RedShadrockOre),RecipeCategory.MISC,ItemInit.ShadrockIngot,0.7f,100,"shadrock");
        offerBlasting(consumer, List.of(BlockInit.DarkAsheniteOre,BlockInit.LightAsheniteOre,BlockInit.RedAsheniteOre),RecipeCategory.MISC,ItemInit.Ashenite,0.7f,100,"ashenite");
        offerSmelting(consumer, List.of(ItemInit.RawEclipsium,BlockInit.DarkEclipsiumOre,BlockInit.LightEclipsiumOre,BlockInit.RedEclipsiumOre),RecipeCategory.MISC,ItemInit.EclipsiumIngot,0.7f,200,"eclipsium");
        offerSmelting(consumer, List.of(ItemInit.RawShadrock,BlockInit.DarkShadrockOre,BlockInit.LightShadrockOre,BlockInit.RedShadrockOre),RecipeCategory.MISC,ItemInit.ShadrockIngot,0.7f,200,"shadrock");
        offerSmelting(consumer, List.of(BlockInit.DarkAsheniteOre,BlockInit.LightAsheniteOre,BlockInit.RedAsheniteOre),RecipeCategory.MISC,ItemInit.Ashenite,0.7f,200,"ashenite");
    }

}