package com.cosmo.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class SoulCraftingRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final List<Ingredient> inputs;
    private final ItemStack output;

    public SoulCraftingRecipe(Identifier id, List<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient) return false;
        boolean bool = inputs.get(0).test(inventory.getStack(0))
                &&inputs.get(1).test(inventory.getStack(1))
                &&inputs.get(2).test(inventory.getStack(2))
                &&inputs.get(3).test(inventory.getStack(3))
                &&inputs.get(4).test(inventory.getStack(4))
                &&inputs.get(5).test(inventory.getStack(5))
                &&inputs.get(6).test(inventory.getStack(6))
                &&inputs.get(7).test(inventory.getStack(7));
        return bool;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.inputs.size());
        list.addAll(inputs);
        return list;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SoulCraftingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "soul_crafting";
    }

    public static class Serializer implements RecipeSerializer<SoulCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "soul_crafting";
        @Override
        public SoulCraftingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json,"output"));
            JsonArray ingredients = JsonHelper.getArray(json,"ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(8,Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i,Ingredient.fromJson(ingredients.get(i)));
            }
            return new SoulCraftingRecipe(id, inputs, output);
        }

        @Override
        public SoulCraftingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(),Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            return new SoulCraftingRecipe(id,inputs,output);
        }

        @Override
        public void write(PacketByteBuf buf, SoulCraftingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.output);
        }
    }
}
