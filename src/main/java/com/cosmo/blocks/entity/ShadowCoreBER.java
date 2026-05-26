package com.cosmo.blocks.entity;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class ShadowCoreBER extends GeoBlockRenderer<ShadowCoreBlockEntity> {
    public ShadowCoreBER(BlockEntityRendererFactory.Context context) {
        super(new ShadowCoreModel());
    }
}
