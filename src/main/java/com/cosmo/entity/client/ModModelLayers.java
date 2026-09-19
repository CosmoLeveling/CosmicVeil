package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
	public static final EntityModelLayer WEEPER =
		new EntityModelLayer(new Identifier(CosmicVeil.MOD_ID,"weeper"),"main");
    public static final EntityModelLayer SCORCHED =
            new EntityModelLayer(new Identifier(CosmicVeil.MOD_ID,"scorched"),"main");
    public static final EntityModelLayer SOLAR_SMASHER =
            new EntityModelLayer(new Identifier(CosmicVeil.MOD_ID,"solar_smasher"),"main");
	public static final EntityModelLayer TRAP_BULLET =
			new EntityModelLayer(new Identifier(CosmicVeil.MOD_ID,"trap_bullet"),"main");
}
