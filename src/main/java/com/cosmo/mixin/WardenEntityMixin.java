package com.cosmo.mixin;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.entity.custom.WeeperEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin {

    @Inject(method = "isValidTarget", at = @At("HEAD"), cancellable = true)
    private void preventTargetingShifted(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity player) {
            if (CosmicVeilComponents.SHIFTED.get(player).isShifted()) {
                cir.setReturnValue(false); // Prevent targeting
            }
        }

        if (entity instanceof WeeperEntity) {
            cir.setReturnValue(false);
        }
    }
}
