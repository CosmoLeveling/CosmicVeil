package com.cosmo.blocks.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class DarkPillarBER implements BlockEntityRenderer<DarkPillarBlockEntity> {
    protected final BlockEntityRenderDispatcher dispatcher;
    public DarkPillarBER(BlockEntityRendererFactory.Context context) {
        this.dispatcher = context.getRenderDispatcher();
    }

    @Override
    public void render(DarkPillarBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStoredItem();

        if (!stack.isEmpty()) {
            matrices.push();
            // Make the item float higher
            double yOffset = 1.55 + 0.05 * Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0);
            matrices.translate(0.5, yOffset, 0.5);
            // Rotate the item
            float rotation = (System.currentTimeMillis() / 20) % 360;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
            // Render the item
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            matrices.scale(1.2f,1.2f,1.2f);
            itemRenderer.renderItem(stack, ModelTransformationMode.GROUND,
                    light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
            matrices.pop();
        } else {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            itemRenderer.renderItem(ItemStack.EMPTY, ModelTransformationMode.GROUND,
                    light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        }
    }
}
