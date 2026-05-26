package com.cosmo.mixin.client;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.init.ItemInit;
import com.cosmo.items.MonarchsBlade;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Shadow
    public abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color);

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            at = @At("HEAD"))
    private void Cosmic_Veil$drawCustomBar(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Check for your custom item
        if (stack.isOf(ItemInit.ShadowMirror)) {
            // Example: Get your custom charge % (0.0–1.0)
            float charge = CosmicVeilComponents.SHIFTED.get(client.player).getShiftedTimer();

            // Colors (ARGB)
            int bgColor = 0xFF000000; // black
            int barColor = 0xFFAA00FF;

            // Draw the background bar (x1, y1, x2, y2)
            this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 15, y + 14, bgColor);

            // Draw the fill bar — scales with charge
            int barWidth = Math.round(13 * Math.min(charge, 1.0f));
            this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 2 + barWidth, y + 14, barColor);
        }

        if (stack.isOf(ItemInit.MonarchsSword)) {
            if (stack.getItem() instanceof MonarchsBlade monarchsBlade) {
                if (MonarchsBlade.getStance(stack) == 2) {
                    // Example: Get your custom charge % (0.0–1.0)
                    float charge = CosmicVeilComponents.MonarchSwordComponent.get(client.player).getDarkCooldown();

                    // Colors (ARGB)
                    int bgColor = 0xFF000000; // black
                    int barColor = 0xFFAA00FF;

                    // Draw the background bar (x1, y1, x2, y2)
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 15, y + 14, bgColor);

                    // Draw the fill bar — scales with charge
                    int barWidth = Math.round(13 * Math.min(charge, 1.0f));
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 2 + barWidth, y + 14, barColor);
                }
                if (MonarchsBlade.getStance(stack) == 1) {
                    // Example: Get your custom charge % (0.0–1.0)
                    float charge = CosmicVeilComponents.MonarchSwordComponent.get(client.player).getLightCooldown();

                    // Colors (ARGB)
                    int bgColor = 0xFF000000; // black
                    int barColor = 0xFFAA00FF;

                    // Draw the background bar (x1, y1, x2, y2)
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 15, y + 14, bgColor);

                    // Draw the fill bar — scales with charge
                    int barWidth = Math.round(13 * Math.min(charge, 1.0f));
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 2 + barWidth, y + 14, barColor);
                }
                if (MonarchsBlade.getStance(stack) == 0) {
                    // Example: Get your custom charge % (0.0–1.0)
                    float charge = CosmicVeilComponents.MonarchSwordComponent.get(client.player).getBalancedCooldown();

                    // Colors (ARGB)
                    int bgColor = 0xFF000000; // black
                    int barColor = 0xFFAA00FF;

                    // Draw the background bar (x1, y1, x2, y2)
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 15, y + 14, bgColor);

                    // Draw the fill bar — scales with charge
                    int barWidth = Math.round(13 * Math.min(charge, 1.0f));
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 2 + barWidth, y + 14, barColor);
                }
            }
        }
    }
}