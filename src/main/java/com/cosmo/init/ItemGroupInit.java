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
    public static final ItemGroup TEST_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ItemInit.ShadowMirror))
            .displayName(Text.translatable("itemGroup.cosmic_veil.cosmic_veil_group"))
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
                if (FabricLoader.getInstance().isModLoaded(""))
                entries.add(Registries.ITEM.get(Identifier.of("minecraft","string")));
            })
            .build();
    public static void RegisterItemGroups(){
        Registry.register(Registries.ITEM_GROUP,new Identifier(CosmicVeil.MOD_ID,"cosmic_veil_group"),TEST_GROUP);
    }
}
