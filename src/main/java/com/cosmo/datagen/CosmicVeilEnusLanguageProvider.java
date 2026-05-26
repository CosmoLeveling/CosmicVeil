package com.cosmo.datagen;

import com.cosmo.CosmicVeil;
import com.cosmo.init.BlockInit;
import com.cosmo.init.EntityInit;
import com.cosmo.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class CosmicVeilEnusLanguageProvider extends FabricLanguageProvider {
    public CosmicVeilEnusLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ItemInit.SHADOW_BOOTS,"Shadow Imbued Boots");
        translationBuilder.add(ItemInit.SHADOW_LEGGINGS,"Shadow Imbued Leggings");
        translationBuilder.add(ItemInit.SHADOW_CHESTPLATE,"Shadow Imbued Chestplate");
        translationBuilder.add(ItemInit.SHADOW_HELMET,"Shadow Imbued Helmet");
        translationBuilder.add(ItemInit.MonarchsSword,"Monarch's sword");
        translationBuilder.add(ItemInit.ShadowMirror,"Shadow Mirror");
        translationBuilder.add(ItemInit.ShadowTrap,"Shadow Trap");
        translationBuilder.add(ItemInit.WeeperTotem,"Weeper Totem");
        translationBuilder.add(ItemInit.WeeperSpawnEgg,"Weeper Spawn Egg");
        translationBuilder.add(ItemInit.ShadowCalibrator,"Shadow Calibrator");
        translationBuilder.add(BlockInit.ShadowTransporter,"Shadow Transporter");
        translationBuilder.add(EntityInit.WEEPER,"Weeper");
        translationBuilder.add("emi.category.cosmic_veil.soul_crafter","Soul Crafting");
        translationBuilder.add(BlockInit.DarkShadowRockLight,"Dark Shadow Rock Light");
        translationBuilder.add(BlockInit.RedShadowRockLight,"Red Shadow Rock Light");
        translationBuilder.add(BlockInit.LightShadowRockLight,"Light Shadow Rock Light");
        translationBuilder.add(BlockInit.ShadowPortal,"Shadow Portal");
        translationBuilder.add(ItemInit.MarksmanVeil,"Marksman Veil");
        translationBuilder.add(ItemInit.ShadowShield,"Shadow Shield");
        translationBuilder.add("itemGroup.cosmic_veil.cosmic_veil_group","Cosmic Veil");
        translationBuilder.add("cosmic_veil.container.crafting","Soul Crafter");
        translationBuilder.add(BlockInit.DarkShadowRock,"Dark Shadow Rock");
        translationBuilder.add(BlockInit.DarkShadowRockButton,"Dark Shadow Rock Button");
        translationBuilder.add(BlockInit.DarkShadowRockWall,"Dark Shadow Rock Wall");
        translationBuilder.add(BlockInit.DarkShadowRockSlab,"Dark Shadow Rock Slab");
        translationBuilder.add(BlockInit.DarkShadowRockStair,"Dark Shadow Rock Stairs");
        translationBuilder.add(BlockInit.DarkShadowRockPressurePlate,"Dark Shadow Rock Pressure Plate");

        translationBuilder.add(BlockInit.DarkPedestal,"Dark Pedestal");
        translationBuilder.add(BlockInit.ShadowCore, "Shadow Core");
        translationBuilder.add(BlockInit.RedShadowRock,"Red Shadow Rock");
        translationBuilder.add(BlockInit.RedShadowRockButton,"Red Shadow Rock Button");
        translationBuilder.add(BlockInit.RedShadowRockWall,"Red Shadow Rock Wall");
        translationBuilder.add(BlockInit.RedShadowRockSlab,"Red Shadow Rock Slab");
        translationBuilder.add(BlockInit.RedShadowRockStair,"Red Shadow Rock Stairs");
        translationBuilder.add(BlockInit.RedShadowRockPressurePlate,"Red Shadow Rock Pressure Plate");

        translationBuilder.add(BlockInit.LightShadowRock,"Light Shadow Rock");
        translationBuilder.add(BlockInit.LightShadowRockButton,"Light Shadow Rock Button");
        translationBuilder.add(BlockInit.LightShadowRockWall,"Light Shadow Rock Wall");
        translationBuilder.add(BlockInit.LightShadowRockSlab,"Light Shadow Rock Slab");
        translationBuilder.add(BlockInit.LightShadowRockStair,"Light Shadow Rock Stairs");
        translationBuilder.add(BlockInit.LightShadowRockPressurePlate,"Light Shadow Rock Pressure Plate");

        translationBuilder.add(BlockInit.DarkShadrockOre,"Dark Shadrock Ore");
        translationBuilder.add(BlockInit.RedShadrockOre,"Red Shadrock Ore");
        translationBuilder.add(BlockInit.LightShadrockOre,"Light Shadrock Ore");
        translationBuilder.add(BlockInit.DarkEclipsiumOre,"Dark Eclipsium Ore");
        translationBuilder.add(BlockInit.RedEclipsiumOre,"Red Eclipsium Ore");
        translationBuilder.add(BlockInit.LightEclipsiumOre,"Light Eclipsium Ore");
        translationBuilder.add(BlockInit.DarkAsheniteOre,"Dark Ashenite Ore");
        translationBuilder.add(BlockInit.RedAsheniteOre,"Red Ashenite Ore");
        translationBuilder.add(BlockInit.LightAsheniteOre,"Light Ashenite Ore");

        translationBuilder.add(ItemInit.RawEclipsium,"Raw Eclipsium");
        translationBuilder.add(ItemInit.RawShadrock,"Raw Shadrock");
        translationBuilder.add(ItemInit.Ashenite,"Ashenite");
        translationBuilder.add(ItemInit.EclipsiumIngot,"Eclipsium Ingot");
        translationBuilder.add(ItemInit.ShadrockIngot,"Shadrock Ingot");
        translationBuilder.add(ItemInit.EclipsiumNugget,"Eclipsium Nugget");
        translationBuilder.add(ItemInit.ShadrockNugget,"Shadrock Nugget");
        translationBuilder.add(BlockInit.AsheniteBlock,"Ashenite Block");
        translationBuilder.add(BlockInit.EclipsiumBlock,"Eclipsium Block");
        translationBuilder.add(BlockInit.ShadrockBlock,"Shadrock Block");

        translationBuilder.add(ItemInit.ShadowImbuer, "Shadow Imbuer");
        translationBuilder.add(ItemInit.WeeperPearl, "Weeper Pearl");
        translationBuilder.add(ItemInit.ShadowCompass, "Shadow Compass");

        translationBuilder.add(CosmicVeil.ShadowSight,"Shadow Sight");
    }
}
