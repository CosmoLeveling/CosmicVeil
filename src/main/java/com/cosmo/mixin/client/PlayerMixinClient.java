package com.cosmo.mixin.client;

import com.cosmo.CosmicVeilComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerMixinClient {
    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void shadowName(AbstractClientPlayerEntity abstractClientPlayerEntity, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
            if (abstractClientPlayerEntity.getWorld().isClient()) {
                if (MinecraftClient.getInstance().player != null) {
                    if (!CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).shouldSeeThrough()) {
                        if (CosmicVeilComponents.SHIFTED.get(abstractClientPlayerEntity).isShifted()) ci.cancel();
                    }
                }
            }
    }
}
