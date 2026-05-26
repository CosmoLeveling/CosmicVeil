package com.cosmo.rendering;

import com.cosmo.CosmicVeil;
import ladysnake.satin.api.event.PostWorldRenderCallbackV2;
import ladysnake.satin.api.experimental.ReadableDepthFramebuffer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.util.GlMatrices;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;

import java.util.List;

public class WorldBasedShader implements PostWorldRenderCallbackV2, ClientTickEvents.EndTick {
    public static final WorldBasedShader INSTANCE = new WorldBasedShader();
    private static final ManagedShaderEffect SHADER = ShaderEffectManager.getInstance()
            .manage(new Identifier(CosmicVeil.MOD_ID, "shaders/post/world_based.json"), (managedShaderEffect -> {
                managedShaderEffect.setSamplerUniform("DepthSampler",((ReadableDepthFramebuffer)MinecraftClient.getInstance().getFramebuffer()).getStillDepthMap());
            }));
    private final Matrix4f projectionMatrix = new Matrix4f();
    @Override
    public void onEndTick(MinecraftClient minecraftClient) {

    }

    public List<BlockPos> positions = List.of(
            new BlockPos(0,80,0),
            new BlockPos(0,75,0),
            new BlockPos(0,70,0),
            new BlockPos(0,65,0),
            new BlockPos(0,60,0)
            );

    @Override
    public void onWorldRendered(MatrixStack matrices, Camera camera, float tickDelta, long nanoTime) {
        if (MinecraftClient.getInstance().player != null) {
            camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            SHADER.setUniformValue("InverseTransformMatrix",GlMatrices.getInverseTransformMatrix(projectionMatrix));
            SHADER.setUniformValue("CameraPosition",camera.getPos().toVector3f().x,camera.getPos().toVector3f().y,camera.getPos().toVector3f().z);
            for (BlockPos pos : positions) {
                SHADER.setUniformValue("BlockPosition", (float) pos.getX(), (float) pos.getY(), (float) pos.getZ());
                SHADER.render(tickDelta);
            }
        }
    }
}
