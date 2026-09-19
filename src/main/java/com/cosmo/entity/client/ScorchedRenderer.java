package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.ScorchedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ScorchedRenderer extends MobEntityRenderer<ScorchedEntity,ScorchedModel<ScorchedEntity>> {
    private static final Identifier TEXTURE = new Identifier(CosmicVeil.MOD_ID,"textures/entity/scorched.png");

    public ScorchedRenderer(EntityRendererFactory.Context context) {
        super(context, new ScorchedModel<>(context.getPart(ModModelLayers.SCORCHED)), 0.6f);
    }

    @Override
    public Identifier getTexture(ScorchedEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(ScorchedEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
