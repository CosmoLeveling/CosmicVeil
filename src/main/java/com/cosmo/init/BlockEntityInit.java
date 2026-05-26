package com.cosmo.init;

import com.cosmo.CosmicVeil;
import com.cosmo.blocks.entity.DarkPillarBlockEntity;
import com.cosmo.blocks.entity.ShadowCoreBlockEntity;
import com.cosmo.blocks.entity.ShadowTransporterBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityInit {
    public static BlockEntityType<ShadowTransporterBlockEntity> ShadowTransporterBlockEntityType =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(CosmicVeil.MOD_ID,"shadow_transporter_be"),
                    FabricBlockEntityTypeBuilder.create(ShadowTransporterBlockEntity::new, BlockInit.ShadowTransporter).build());
    public static BlockEntityType<ShadowCoreBlockEntity> ShadowCoreBlockEntityType =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(CosmicVeil.MOD_ID,"shadow_core_be"),
                    FabricBlockEntityTypeBuilder.create(ShadowCoreBlockEntity::new, BlockInit.ShadowCore).build());

    public static BlockEntityType<DarkPillarBlockEntity> DarkPillarBlockEntityType =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(CosmicVeil.MOD_ID,"dark_pillar_be"),
                    FabricBlockEntityTypeBuilder.create(DarkPillarBlockEntity::new, BlockInit.DarkPedestal).build());

    public static void init()
    {}
}
