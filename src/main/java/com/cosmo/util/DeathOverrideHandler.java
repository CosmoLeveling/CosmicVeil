package com.cosmo.util;

import com.cosmo.world.dimension.DimensionInit;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class DeathOverrideHandler {
    public static void register() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return true;

            if (player.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)&&player.getServer()!=null) {
                // Prevent death
                player.setHealth(player.getMaxHealth());
                ServerWorld world = player.getServer().getWorld(DimensionInit.SHADOW_REALM_LEVEL_KEY);// Or any dimension
                if (world!=null) {
                    TeleportUtils.teleportPlayerToDimension(player, player.getServer(), world.getRegistryKey());
                    return false; // Cancel death
                }
            }

            return true; // Allow death
        });
    }
}
