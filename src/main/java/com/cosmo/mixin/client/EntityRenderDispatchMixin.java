package com.cosmo.mixin.client;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.world.dimension.DimensionInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatchMixin {
	@Inject(method = "renderShadow", at = @At("HEAD"),cancellable = true)
	private static void renderShadow(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, float opacity, float tickDelta, WorldView world, float radius, CallbackInfo ci){
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
                        if (!CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()&&!((WeeperEntity) entity).isOwner(MinecraftClient.getInstance().player)&&!((WeeperEntity) entity).isForcedTarget(MinecraftClient.getInstance().player))
                            ci.cancel();
					}
				}
			}
		}
	}
	@Inject(method = "renderHitbox", at = @At("HEAD"),cancellable = true)
	private static void renderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo ci) {
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
                        if (!CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()&&!((WeeperEntity) entity).isOwner(MinecraftClient.getInstance().player)&&!((WeeperEntity) entity).isForcedTarget(MinecraftClient.getInstance().player))
                            ci.cancel();
					}
				}
			}
		}
	}
	@Inject(method = "renderFire",at = @At("HEAD"),cancellable = true)
	private void renderFire(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Entity entity, CallbackInfo ci) {
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
                        if (!CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()&&!((WeeperEntity) entity).isOwner(MinecraftClient.getInstance().player)&&!((WeeperEntity) entity).isForcedTarget(MinecraftClient.getInstance().player))
                            ci.cancel();
					}
				}
			}
		}
	}
}
