package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.recipe.SoulCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeInit {
    public static void init() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CosmicVeil.MOD_ID, SoulCraftingRecipe.Serializer.ID),
                SoulCraftingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(CosmicVeil.MOD_ID, SoulCraftingRecipe.Type.ID),
                SoulCraftingRecipe.Type.INSTANCE);
    }
}
