package com.cosmo;

import com.cosmo.blocks.entity.DarkPillarBER;
import com.cosmo.blocks.entity.ShadowCoreBER;
import com.cosmo.entity.client.*;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.init.BlockEntityInit;
import com.cosmo.init.BlockInit;
import com.cosmo.init.EntityInit;
import com.cosmo.init.ItemInit;
import com.cosmo.rendering.ShiftedShader;
import com.cosmo.world.dimension.DimensionInit;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3i;

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
        PlayerBlockBreakEvents.BEFORE.register(((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if (playerEntity.isCreative()){
                return true;
            }
            if (playerEntity.getWorld().getRegistryKey() == DimensionInit.SOLAR_REALM_LEVEL_KEY){
                return !blockPos.isWithinDistance(new Vec3i(15, 104, 15), 128);
            }
            return true;
        }));
        UseBlockCallback.EVENT.register(((playerEntity, world, hand, blockHitResult) -> {
            BlockPos blockPos=blockHitResult.getBlockPos();
            if (playerEntity.getWorld().getRegistryKey() != DimensionInit.SOLAR_REALM_LEVEL_KEY) {
                return ActionResult.PASS;
            }
            if (!blockPos.isWithinDistance(new Vec3i(15, 104, 15), 128)) {
                return ActionResult.PASS;
            }
            if (playerEntity.isCreative()){
                return ActionResult.PASS;
            }
            if (!world.getBlockState(blockHitResult.getBlockPos()).isOf(BlockInit.SolarVinesBlossom)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        }));
        EntityRendererRegistry.register(EntityInit.WEEPER, WeeperRenderer::new);
        EntityRendererRegistry.register(EntityInit.SOLAR_SMASHER, SolarSmasherRenderer::new);
        EntityRendererRegistry.register(EntityInit.SCORCHED, ScorchedRenderer::new);
        EntityRendererRegistry.register(EntityInit.TRAP_BULLET, TrapBulletRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.WEEPER, WeeperModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SCORCHED, ScorchedModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TRAP_BULLET, TrapBulletModel::getTexturedModelData);
        BlockEntityRendererFactories.register(BlockEntityInit.DarkPillarBlockEntityType, DarkPillarBER::new);
        BlockEntityRendererFactories.register(BlockEntityInit.ShadowCoreBlockEntityType, ShadowCoreBER::new);
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SolarVinesBlossom, RenderLayer.getCutout());
    }
}
