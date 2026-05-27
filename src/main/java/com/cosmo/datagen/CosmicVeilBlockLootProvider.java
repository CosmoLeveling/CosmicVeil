package com.cosmo.datagen;

import com.cosmo.init.BlockInit;
import com.cosmo.init.ItemInit;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;

public class CosmicVeilBlockLootProvider extends FabricBlockLootTableProvider {
    public CosmicVeilBlockLootProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(BlockInit.ShadowTransporter);
        addDrop(BlockInit.ShadowPortal);
        addDrop(BlockInit.DarkShadowRockLight);
        addDrop(BlockInit.DarkShadowRock);
        addDrop(BlockInit.DarkShadowRockButton);
        addDrop(BlockInit.DarkShadowRockWall);
        addDrop(BlockInit.DarkShadowRockStair);
        addDrop(BlockInit.DarkShadowRockSlab);
        addDrop(BlockInit.DarkShadowRockPressurePlate);

        addDrop(BlockInit.SolarRock);
        addDrop(BlockInit.LightSolarRock);
        addDrop(BlockInit.DarkSolarRock);

        addDrop(BlockInit.DarkPedestal);
        addDrop(BlockInit.ShadowCore);
        addDrop(BlockInit.RedShadowRockLight);
        addDrop(BlockInit.RedShadowRock);
        addDrop(BlockInit.RedShadowRockButton);
        addDrop(BlockInit.RedShadowRockWall);
        addDrop(BlockInit.RedShadowRockStair);
        addDrop(BlockInit.RedShadowRockSlab);
        addDrop(BlockInit.RedShadowRockPressurePlate);

        addDrop(BlockInit.LightShadowRockLight);
        addDrop(BlockInit.LightShadowRock);
        addDrop(BlockInit.LightShadowRockButton);
        addDrop(BlockInit.LightShadowRockWall);
        addDrop(BlockInit.LightShadowRockStair);
        addDrop(BlockInit.LightShadowRockSlab);
        addDrop(BlockInit.LightShadowRockPressurePlate);

        addDrop(BlockInit.DarkAsheniteOre,oreDrops(BlockInit.DarkAsheniteOre, ItemInit.Ashenite));
        addDrop(BlockInit.LightAsheniteOre,oreDrops(BlockInit.LightAsheniteOre, ItemInit.Ashenite));
        addDrop(BlockInit.RedAsheniteOre,oreDrops(BlockInit.RedAsheniteOre, ItemInit.Ashenite));
        addDrop(BlockInit.DarkShadrockOre,oreDrops(BlockInit.DarkShadrockOre, ItemInit.RawShadrock));
        addDrop(BlockInit.LightShadrockOre,oreDrops(BlockInit.LightShadrockOre, ItemInit.RawShadrock));
        addDrop(BlockInit.RedShadrockOre,oreDrops(BlockInit.RedShadrockOre, ItemInit.RawShadrock));
        addDrop(BlockInit.DarkEclipsiumOre,oreDrops(BlockInit.DarkEclipsiumOre, ItemInit.RawEclipsium));
        addDrop(BlockInit.LightEclipsiumOre,oreDrops(BlockInit.LightEclipsiumOre, ItemInit.RawEclipsium));
        addDrop(BlockInit.RedEclipsiumOre,oreDrops(BlockInit.LightEclipsiumOre, ItemInit.RawEclipsium));
        addDrop(BlockInit.ShadrockBlock);
        addDrop(BlockInit.EclipsiumBlock);
        addDrop(BlockInit.AsheniteBlock);
    }
    public LootTable.Builder stoneDrops(Block dropWithSilkTouch, Item drop) {
        return dropsWithSilkTouch(dropWithSilkTouch, this.applyExplosionDecay(dropWithSilkTouch, ItemEntry.builder(drop)));
    }
}
