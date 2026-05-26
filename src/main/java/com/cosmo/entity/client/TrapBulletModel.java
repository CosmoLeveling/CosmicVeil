package com.cosmo.entity.client;

import com.cosmo.entity.custom.TrapBullet;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class TrapBulletModel extends EntityModel<TrapBullet> {
	private final ModelPart Bullet;
	public TrapBulletModel(ModelPart root) {
		this.Bullet = root.getChild("Bullet");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Bullet = modelPartData.addChild("Bullet", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 4).cuboid(-2.0F, -2.5F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(8, 4).cuboid(5.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(TrapBullet entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Bullet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}