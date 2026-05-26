package com.cosmo.mixin;

import com.cosmo.util.ShiftedComponent;
import dev.doctor4t.arsenal.entity.AnchorbladeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(AnchorbladeEntity.class)
public abstract class MixinAnchorbladeEntity {
    @Inject(method = "onEntityHit",at = @At("HEAD"),cancellable = true)
    public void hit(EntityHitResult entityHitResult, CallbackInfo ci){
        Entity entity = entityHitResult.getEntity();
        if ((Object) this instanceof AnchorbladeEntity projectile) {
            if (!ShiftedComponent.shiftedCheck(projectile.getOwner(),entity)) {
                ci.cancel();
            }
        }
    }
}
