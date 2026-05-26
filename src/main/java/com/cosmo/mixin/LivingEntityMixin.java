package com.cosmo.mixin;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.util.ShiftedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "pushAway",at = @At("HEAD"),cancellable = true)
    public void push(Entity entity, CallbackInfo ci) {
        if (!((Object) this instanceof LivingEntity livingEntity)) return;
        if (!ShiftedComponent.canSeePlayer(livingEntity,entity)) ci.cancel();
    }

    @Inject(method = "pushAwayFrom",at = @At("HEAD"),cancellable = true)
    public void pushAwayFrom(Entity entity, CallbackInfo ci) {
        if (!((Object) this instanceof LivingEntity livingEntity)) return;
        if (!ShiftedComponent.canSeePlayer(livingEntity,entity)) ci.cancel();
    }

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z",at = @At("HEAD"),cancellable = true)
    public void canTarget(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        if (target instanceof PlayerEntity player){
            if (CosmicVeilComponents.SHIFTED.get(player).isShifted()){
                cir.setReturnValue(false);
            }
        }
    }
}
