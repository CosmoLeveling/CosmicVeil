package com.cosmo.init;

import com.cosmo.CosmicVeil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroupInit {
    public static final ItemGroup SHADOW_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.ShadowMirror))
            .displayName(Text.translatable("itemGroup.cosmic_veil.shadow_group"))
            .entries((context, entries) -> {
                entries.add(BlockInit.DarkShadowRock);
                entries.add(BlockInit.DarkShadowRockStair);
                entries.add(BlockInit.DarkShadowRockWall);
                entries.add(BlockInit.DarkShadowRockSlab);
                entries.add(BlockInit.DarkShadowRockPressurePlate);
                entries.add(BlockInit.DarkShadowRockButton);
                entries.add(ItemInit.EclipsiumNugget);
                entries.add(ItemInit.ShadrockNugget);
                entries.add(ItemInit.Ashenite);
                entries.add(BlockInit.LightShadowRock);
                entries.add(BlockInit.LightShadowRockStair);
                entries.add(BlockInit.LightShadowRockWall);
                entries.add(BlockInit.LightShadowRockSlab);
                entries.add(BlockInit.LightShadowRockPressurePlate);
                entries.add(BlockInit.LightShadowRockButton);
                entries.add(ItemInit.EclipsiumIngot);
                entries.add(ItemInit.ShadrockIngot);
                entries.add(ItemInit.ShadowImbuer);
                entries.add(BlockInit.RedShadowRock);
                entries.add(BlockInit.RedShadowRockStair);
                entries.add(BlockInit.RedShadowRockWall);
                entries.add(BlockInit.RedShadowRockSlab);
                entries.add(BlockInit.RedShadowRockPressurePlate);
                entries.add(BlockInit.RedShadowRockButton);
                entries.add(BlockInit.DarkEclipsiumOre);
                entries.add(BlockInit.DarkShadrockOre);
                entries.add(BlockInit.DarkAsheniteOre);
                entries.add(ItemInit.ShadowShield);
                entries.add(ItemInit.WeeperTotem);
                entries.add(ItemInit.WeeperPearl);
                entries.add(ItemInit.ShadowTrap);
                entries.add(ItemInit.RawShadrock);
                entries.add(ItemInit.RawEclipsium);
                entries.add(BlockInit.RedEclipsiumOre);
                entries.add(BlockInit.RedShadrockOre);
                entries.add(BlockInit.RedAsheniteOre);
                entries.add(ItemInit.SHADOW_BOOTS);
                entries.add(ItemInit.SHADOW_LEGGINGS);
                entries.add(ItemInit.SHADOW_CHESTPLATE);
                entries.add(ItemInit.SHADOW_HELMET);
                entries.add(ItemInit.ShadowTemplate);
                entries.add(ItemInit.MonarchsSword);
                entries.add(BlockInit.LightEclipsiumOre);
                entries.add(BlockInit.LightShadrockOre);
                entries.add(BlockInit.LightAsheniteOre);
                entries.add(BlockInit.ShadowPortal);
                entries.add(BlockInit.ShadowTransporter);
                entries.add(BlockInit.DarkPedestal);
                entries.add(BlockInit.ShadowCore);
                entries.add(ItemInit.WeeperSpawnEgg);
                entries.add(ItemInit.MarksmanVeil);
                entries.add(BlockInit.EclipsiumBlock);
                entries.add(BlockInit.ShadrockBlock);
                entries.add(BlockInit.AsheniteBlock);
                entries.add(ItemInit.ShadowMirror);
                entries.add(ItemInit.ShadowCompass);
                entries.add(ItemInit.ShadowCalibrator);
                entries.add(BlockInit.DarkShadowRockLight);
                entries.add(BlockInit.RedShadowRockLight);
                entries.add(BlockInit.LightShadowRockLight);
            })
            .build();
    public static final ItemGroup SOLAR_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(BlockInit.SolarLight))
            .displayName(Text.translatable("itemGroup.cosmic_veil.solar_group"))
            .entries((context, entries) -> {
                entries.add(BlockInit.DarkSolarRock);
                entries.add(BlockInit.DarkSolarRockStair);
                entries.add(BlockInit.DarkSolarRockWall);
                entries.add(BlockInit.DarkSolarRockSlab);
                entries.add(BlockInit.DarkSolarRockPressurePlate);
                entries.add(BlockInit.DarkSolarRockButton);
                entries.add(BlockInit.SolarRock);
                entries.add(BlockInit.SolarRockStair);
                entries.add(BlockInit.SolarRockWall);
                entries.add(BlockInit.SolarRockSlab);
                entries.add(BlockInit.SolarRockPressurePlate);
                entries.add(BlockInit.SolarRockButton);
                entries.add(BlockInit.LightSolarRock);
                entries.add(BlockInit.LightSolarRockStair);
                entries.add(BlockInit.LightSolarRockWall);
                entries.add(BlockInit.LightSolarRockSlab);
                entries.add(BlockInit.LightSolarRockPressurePlate);
                entries.add(BlockInit.LightSolarRockButton);
                entries.add(BlockInit.SolarLight);
                    }
             )
            .build();
    public static void RegisterItemGroups(){
        Registry.register(Registries.ITEM_GROUP,new Identifier(CosmicVeil.MOD_ID,"shadow_veil_group"), SHADOW_GROUP);
        Registry.register(Registries.ITEM_GROUP,new Identifier(CosmicVeil.MOD_ID,"solar_veil_group"), SOLAR_GROUP);
    }
}
