package com.cosmo.entity.client;

import com.cosmo.CosmicVeil;
import com.cosmo.entity.custom.TrapBullet;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class TrapBulletRenderer extends ProjectileEntityRenderer<TrapBullet> {
    private static final Identifier TEXTURE = new Identifier(CosmicVeil.MOD_ID,"textures/entity/trap_bullet.png");
    private static TrapBulletModel model;
    public TrapBulletRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new TrapBulletModel(context.getPart(ModModelLayers.TRAP_BULLET));
    }

    @Override
    public void render(TrapBullet entity, float f, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // Rotate in direction of movement
        float interpolatedYaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0f;
        float interpolatedPitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(interpolatedYaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(interpolatedPitch));

        // Scale and render
        matrices.scale(1.0f, 1.0f, 1.0f);
        matrices.translate(0,-1.25,0);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(model.getLayer(TEXTURE));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(TrapBullet entity) {
        return TEXTURE;
    }
}
