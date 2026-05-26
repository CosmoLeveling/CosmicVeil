package com.cosmo.mixin;

import com.cosmo.util.ShiftedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileMixin {
    @Inject(method = "onEntityHit",at = @At("HEAD"),cancellable = true)
    public void hit(EntityHitResult entityHitResult, CallbackInfo ci){
        Entity entity = entityHitResult.getEntity();
        if ((Object) this instanceof PersistentProjectileEntity projectile) {
            if (!ShiftedComponent.shiftedCheck(projectile.getOwner(),entity)) {
                ci.cancel();
            }
        }
    }
}
