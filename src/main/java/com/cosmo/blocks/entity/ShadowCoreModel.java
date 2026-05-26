package com.cosmo.blocks.entity;

import com.cosmo.CosmicVeil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShadowCoreModel extends GeoModel<ShadowCoreBlockEntity> {
    @Override
    public Identifier getModelResource(ShadowCoreBlockEntity shadowCoreBlockEntity) {
        return new Identifier(CosmicVeil.MOD_ID,"geo/shadow_core.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowCoreBlockEntity shadowCoreBlockEntity) {
        return new Identifier(CosmicVeil.MOD_ID,"textures/block/shadow_arm.png");
    }

    @Override
    public Identifier getAnimationResource(ShadowCoreBlockEntity shadowCoreBlockEntity) {
        return new Identifier(CosmicVeil.MOD_ID,"animations/shadow_core.animation.json");
    }
}
