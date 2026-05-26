package com.cosmo.client.render.entity.feature;



import com.cosmo.CosmicVeil;
import com.cosmo.entity.client.WeeperModel;
import com.cosmo.entity.custom.WeeperEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;


public class WeeperGlowFeatureRenderer<T extends WeeperEntity, M extends WeeperModel<T>> extends EyesFeatureRenderer<T, M> {
	private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(CosmicVeil.MOD_ID,"textures/entity/weeper_glow.png"));

	public WeeperGlowFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
		super(featureRendererContext);
	}

	@Override
	public RenderLayer getEyesTexture() {
		return SKIN;
	}

}
