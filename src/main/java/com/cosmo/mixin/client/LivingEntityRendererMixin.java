package com.cosmo.mixin.client;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.world.dimension.DimensionInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

	@Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"),cancellable = true)
	private void invisibleRender(LivingEntity entity, float f, float g, MatrixStack matrixStack,
								 VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player) {
			if (MinecraftClient.getInstance().player!=null){
				if (!CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()){
					if (CosmicVeilComponents.SHIFTED.get(player).isShifted()) ci.cancel();
				}
			}
		}
		if (entity instanceof WeeperEntity) {
			if(MinecraftClient.getInstance().world!=null) {
				if (!MinecraftClient.getInstance().world.getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)) {
					if (MinecraftClient.getInstance().player != null) {
                        PlayerEntity player = MinecraftClient.getInstance().player;
						if (!CosmicVeilComponents.SHIFTED.get(player).shouldSeeThrough()&&!((WeeperEntity) entity).isOwner(player)&&!((WeeperEntity) entity).isForcedTarget(MinecraftClient.getInstance().player))
							ci.cancel();
					}
				}
			}
		}
	}

}
