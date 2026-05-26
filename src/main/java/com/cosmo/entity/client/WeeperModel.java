// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package com.cosmo.entity.client;

import com.cosmo.entity.animation.WeeperAnimations;
import com.cosmo.entity.custom.WeeperEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WeeperModel<T extends WeeperEntity> extends SinglePartEntityModel<T> {
	private final ModelPart weepers;
	private final ModelPart head;
	public WeeperModel(ModelPart root) {
		this.weepers = root.getChild("weepers");
		this.head = weepers.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData weepers = modelPartData.addChild("weepers", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 14.0F, 0.0F));

		ModelPartData head = weepers.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.0F, -5.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData flaps = head.addChild("flaps", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, 0.0F, -3.0F));

		ModelPartData left_flaps = flaps.addChild("left_flaps", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

		ModelPartData cube_r1 = left_flaps.addChild("cube_r1", ModelPartBuilder.create().uv(42, 0).cuboid(0.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.9599F, 0.0F));

		ModelPartData cube_r2 = left_flaps.addChild("cube_r2", ModelPartBuilder.create().uv(42, 0).cuboid(0.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -3.0F, 0.0F, -0.9599F, 0.0F));

		ModelPartData top_flaps = flaps.addChild("top_flaps", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -5.0F, 2.0F));

		ModelPartData cube_r3 = top_flaps.addChild("cube_r3", ModelPartBuilder.create().uv(36, 26).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 2.0F, -0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r4 = top_flaps.addChild("cube_r4", ModelPartBuilder.create().uv(36, 26).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.9599F, 0.0F, 0.0F));

		ModelPartData cube_r5 = top_flaps.addChild("cube_r5", ModelPartBuilder.create().uv(36, 26).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, -0.9599F, 0.0F, 0.0F));

		ModelPartData back_flaps = flaps.addChild("back_flaps", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -3.0F, 6.0F));

		ModelPartData cube_r6 = back_flaps.addChild("cube_r6", ModelPartBuilder.create().uv(16, 39).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, -2.7053F, 0.0F, 0.0F));

		ModelPartData cube_r7 = back_flaps.addChild("cube_r7", ModelPartBuilder.create().uv(36, 38).cuboid(-4.0F, -6.0F, 0.0F, 8.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -2.7053F, 0.0F, 0.0F));

		ModelPartData right_flaps = flaps.addChild("right_flaps", ModelPartBuilder.create(), ModelTransform.pivot(-8.0F, 0.0F, 3.0F));

		ModelPartData cube_r8 = right_flaps.addChild("cube_r8", ModelPartBuilder.create().uv(42, 0).mirrored().cuboid(-5.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.9599F, 0.0F));

		ModelPartData cube_r9 = right_flaps.addChild("cube_r9", ModelPartBuilder.create().uv(42, 0).mirrored().cuboid(-5.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, -3.0F, 0.0F, 0.9599F, 0.0F));

		ModelPartData body = weepers.addChild("body", ModelPartBuilder.create().uv(0, 13).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -1.0F, 0.2182F, 0.0F, 0.0F));

		ModelPartData under_body = body.addChild("under_body", ModelPartBuilder.create().uv(0, 25).cuboid(-4.0F, -2.0F, -2.0F, 8.0F, 7.0F, 5.0F, new Dilation(-0.5F)), ModelTransform.of(0.0F, 6.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

		ModelPartData right_arm = weepers.addChild("right_arm", ModelPartBuilder.create().uv(26, 13).cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -11.0F, -1.0F));

		ModelPartData lower_right_arm = right_arm.addChild("lower_right_arm", ModelPartBuilder.create().uv(26, 26).cuboid(-1.0F, 0.0F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 9.0F, 0.5F));

		ModelPartData left_arm = weepers.addChild("left_arm", ModelPartBuilder.create().uv(26, 13).cuboid(0.0F, -1.0F, -1.0F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -11.0F, -1.0F));

		ModelPartData lower_left_arm = left_arm.addChild("lower_left_arm", ModelPartBuilder.create().uv(26, 26).mirrored().cuboid(-0.9825F, -0.0001F, -1.5F, 2.0F, 10.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.0F, 9.0F, 0.5F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(WeeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw,headPitch);
		this.updateAnimation(entity.idleAnimationState, WeeperAnimations.idle,ageInTicks,1f);

        if (entity.isSitting()) {
            this.weepers.pivotY = 25;
            this.animate(WeeperAnimations.sit);
            return;
        }
        if (entity.isChasing()) {
            this.animateMovement(WeeperAnimations.chase,limbSwing,limbSwingAmount,1,1);
        }else {
            this.animateMovement(WeeperAnimations.walk, limbSwing, limbSwingAmount, 1, 1);
        }
    }
	private void setHeadAngles(float headYaw,float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        weepers.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return weepers;
	}
}