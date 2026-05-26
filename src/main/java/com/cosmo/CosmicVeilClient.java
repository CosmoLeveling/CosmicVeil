package com.cosmo;

import com.cosmo.blocks.entity.DarkPillarBER;
import com.cosmo.blocks.entity.ShadowCoreBER;
import com.cosmo.entity.client.*;
import com.cosmo.init.BlockEntityInit;
import com.cosmo.init.EntityInit;
import com.cosmo.init.ItemInit;
import com.cosmo.rendering.ShiftedShader;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;

public class CosmicVeilClient implements ClientModInitializer {



    @Override
    public void onInitializeClient() {
//        PostWorldRenderCallbackV2.EVENT.register(WorldBasedShader.INSTANCE);
        ShaderEffectRenderCallback.EVENT.register(ShiftedShader.INSTANCE);
        ClientTickEvents.END_CLIENT_TICK.register(ShiftedShader.INSTANCE);
        ModelPredicateProviderRegistry.register(ItemInit.ShadowShield,new Identifier("blocking"),(stack, world, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        ModelPredicateProviderRegistry.register(
                ItemInit.ShadowCompass,
                new Identifier("angle"),
                new CompassAnglePredicateProvider((world, stack, entity) -> {
                    if (!(entity instanceof PlayerEntity owner)) return null;

                    if (CosmicVeilComponents.Compass.get(owner).getPos() == owner.getBlockPos()) {
                        return null;
                    }

                    return GlobalPos.create(owner.getWorld().getRegistryKey(), CosmicVeilComponents.Compass.get(owner).getPos());
                })
        );
        EntityRendererRegistry.register(EntityInit.WEEPER, WeeperRenderer::new);
        BlockEntityRendererFactories.register(BlockEntityInit.ShadowCoreBlockEntityType, ShadowCoreBER::new);
        BlockEntityRendererFactories.register(BlockEntityInit.DarkPillarBlockEntityType, DarkPillarBER::new);
        EntityRendererRegistry.register(EntityInit.TRAP_BULLET, TrapBulletRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.WEEPER, WeeperModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TRAP_BULLET, TrapBulletModel::getTexturedModelData);
    }
}
