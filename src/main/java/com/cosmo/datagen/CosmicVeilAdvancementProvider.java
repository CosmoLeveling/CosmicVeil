package com.cosmo.datagen;

import com.cosmo.CosmicVeil;
import com.cosmo.init.ItemInit;
import com.cosmo.world.dimension.DimensionInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ChangedDimensionCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CosmicVeilAdvancementProvider extends FabricAdvancementProvider {
    public CosmicVeilAdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement rootAdvancement = Advancement.Builder.create()
                .display(
                        ItemInit.ShadowMirror,
                        Text.literal("Enter the shadow realm"),
                        Text.literal("Welcome to the shadow monarchs domain"),
                        new Identifier("textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("enter_shadow_realm", ChangedDimensionCriterion.Conditions.to(DimensionInit.SHADOW_REALM_LEVEL_KEY))
                .build(consumer, CosmicVeil.MOD_ID+"enter_shadow_realm");
        Advancement findShadowImbuer = Advancement.Builder.create()
                .parent(rootAdvancement)
                .display(
                        ItemInit.ShadowMirror,
                        Text.literal("Imbue Darkness"),
                        Text.literal("Use it see what happens"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("find_shadow_imbuer", InventoryChangedCriterion.Conditions.items(ItemInit.ShadowImbuer))
                .build(consumer, CosmicVeil.MOD_ID+"find_shadow_imbuer");
    }
}
