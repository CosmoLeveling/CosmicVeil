package com.cosmo.entity.client;

import com.cosmo.entity.animation.ScorchedAnimations;
import com.cosmo.entity.animation.WeeperAnimations;
import com.cosmo.entity.custom.ScorchedEntity;
import com.cosmo.entity.custom.WeeperEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 5.0.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class ScorchedModel<T extends ScorchedEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	public ScorchedModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(40, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.0F, 0.0F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData core = body.addChild("core", ModelPartBuilder.create().uv(0, 0).cuboid(-4.7395F, -2.5F, -5.0F, 10.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-0.2605F, -6.4772F, 0.0F, 0.0F, -0.4363F, -0.1745F));

		ModelPartData core2 = body.addChild("core2", ModelPartBuilder.create(), ModelTransform.of(0.0183F, -6.3607F, -0.7491F, 0.0F, 1.2566F, 0.5236F));

		ModelPartData cube_r1 = core2.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -2.5F, -5.0F, 10.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		ModelPartData core3 = body.addChild("core3", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -2.5F, -5.0F, 10.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.5662F, 0.0F, 0.0F, 0.0F, 0.3054F));
		return TexturedModelData.of(modelData, 128, 128);
	}
    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw,headPitch);
        this.updateAnimation(entity.idleAnimationState, ScorchedAnimations.idle,ageInTicks,1f);
    }
    private void setHeadAngles(float headYaw,float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
        return root;
    }
}