package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.SolarSmasher;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SolarSmasherRenderer extends GeoEntityRenderer<SolarSmasher> {
  public static final Identifier TEXTURE = new Identifier(CosmicVeil.MOD_ID, "textures/entity/solar_smasher.png");

  public SolarSmasherRenderer(EntityRendererFactory.Context context) {
    super(context, new SolarSmasherModel<>());
  }

  @Override
  public Identifier getTextureLocation(SolarSmasher animatable) {
    return TEXTURE;
  }
}
