package com.cosmo.rendering;

import com.cosmo.CosmicVeil;
import com.cosmo.CosmicVeilComponents;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ShiftedShader implements ShaderEffectRenderCallback, ClientTickEvents.EndTick{
    public static final ShiftedShader INSTANCE = new ShiftedShader();
    private int ticks = 0;
    private static final ManagedShaderEffect SHIFTED_SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(CosmicVeil.MOD_ID, "shaders/post/shifted.json"),(managedShaderEffect -> {
        managedShaderEffect.setSamplerUniform("DepthSampler",((ReadableDepthFramebuffer)MinecraftClient.getInstance().getFramebuffer()).getStillDepthMap());
    }));

    public static int clamp(int value, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        }
        return Math.max(min, Math.min(max, value));
    }
    @Override
    public void onEndTick(MinecraftClient client) {
        if (client.player != null) {
            if (CosmicVeilComponents.SHIFTED.get(client.player).isShifted())
            {
                ++ticks;
            }else{
                --ticks;
            }
            ticks = clamp(ticks,0,40);
        }
    }
    @Override
    public void renderShaderEffects(float tickDelta) {
        if (MinecraftClient.getInstance().player != null) {
            if (CosmicVeilComponents.SHIFTED.get(MinecraftClient.getInstance().player).isShifted()) {
                SHIFTED_SHADER.render(tickDelta);
                SHIFTED_SHADER.setUniformValue("STime",(ticks)/40f);
            } else if (ticks > 0) {
                SHIFTED_SHADER.render(tickDelta);
                SHIFTED_SHADER.setUniformValue("STime",(ticks)/40f);
            }
        }
    }

}
