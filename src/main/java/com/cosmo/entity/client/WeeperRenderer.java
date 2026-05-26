package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import com.cosmo.client.render.entity.feature.WeeperGlowFeatureRenderer;
import com.cosmo.entity.custom.WeeperEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class WeeperRenderer extends MobEntityRenderer<WeeperEntity,WeeperModel<WeeperEntity>> {
	private static final Identifier TEXTURE = new Identifier(CosmicVeil.MOD_ID,"textures/entity/weeper.png");

	public WeeperRenderer(EntityRendererFactory.Context context) {
		super(context, new WeeperModel<>(context.getPart(ModModelLayers.WEEPER)),0.6f);
		this.addFeature(new WeeperGlowFeatureRenderer<>(this));
	}

	@Override
	public Identifier getTexture(WeeperEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(WeeperEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
