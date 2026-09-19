package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.SolarSmasher;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SolarSmasherModel<T extends SolarSmasher> extends GeoModel<T> {
  @Override
  public Identifier getModelResource(T t) {
    return CosmicVeil.id("geo/solar_smasher.geo.json");
  }

  @Override
  public Identifier getTextureResource(T t) {
    return SolarSmasherRenderer.TEXTURE;
  }

  @Override
  public Identifier getAnimationResource(T t) {
    return CosmicVeil.id("animations/solar_smasher.animation.json");
  }
}