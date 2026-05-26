package com.cosmo.mixin.client;

import com.cosmo.init.ItemInit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class PlayerEntityModelMixin {

    @Inject(method = "positionRightArm",at = @At("HEAD"), cancellable = true)
    private void CustomArmPos(LivingEntity entity, CallbackInfo ci){
        if ((Object)this instanceof BipedEntityModel<?> model) {
            if (entity.getMainHandStack().isOf(ItemInit.MarksmanVeil)) {
                CrossbowPosing.hold(model.rightArm, model.leftArm, model.head, true);
                ci.cancel();
            }else{
                if (entity.getOffHandStack().isOf(ItemInit.MarksmanVeil)) {
                    CrossbowPosing.hold(model.rightArm, model.leftArm, model.head, true);
                    ci.cancel();
                }
            }
        }
    }
    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private void CustomLeftArmPos(LivingEntity entity, CallbackInfo ci){
        if ((Object)this instanceof BipedEntityModel<?> model) {
            if (MinecraftClient.getInstance().player.getMainArm() == Arm.LEFT){
                if (entity.getMainHandStack().isOf(ItemInit.MarksmanVeil)) {
                    CrossbowPosing.hold(model.rightArm, model.leftArm, model.head, false);
                    ci.cancel();
                }
            }else{
                if (entity.getOffHandStack().isOf(ItemInit.MarksmanVeil)) {
                    CrossbowPosing.hold(model.rightArm, model.leftArm, model.head, false);
                    ci.cancel();
                }
            }
        }
    }
}
