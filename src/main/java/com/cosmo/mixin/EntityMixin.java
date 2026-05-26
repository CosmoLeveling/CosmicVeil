package com.cosmo.mixin;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.world.dimension.DimensionInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
	public void invis(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		if (player.getWorld() != null) {
			if (!player.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY)) {
				if (CosmicVeilComponents.SHIFTED.get(player).isShifted()) {
					cir.setReturnValue(true);

				}
			}
		}
	}
}
