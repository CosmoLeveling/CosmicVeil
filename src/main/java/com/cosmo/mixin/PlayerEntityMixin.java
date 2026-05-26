package com.cosmo.mixin;

import com.cosmo.CosmicVeil;
import com.cosmo.CosmicVeilComponents;
import com.cosmo.init.ItemInit;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    public abstract void increaseStat(Stat<?> stat, int amount);

    @Shadow
    public abstract void increaseStat(Identifier stat, int amount);

    @Shadow
    public abstract boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource);

    @Inject(method = "disableShield", at = @At("TAIL"))
	private void shieldDisableThing(boolean sprinting, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntity player) {
			float f = 0.25F + (float) EnchantmentHelper.getEfficiency(player) * 0.05F;
			if (sprinting) {
				f += 0.75F;
			}
			if (player.getRandom().nextFloat() < f) {
				player.getItemCooldownManager().set(ItemInit.ShadowShield, 100);
				player.clearActiveItem();
				player.getWorld().sendEntityStatus(player, (byte) 30);
			}
		}
	}
	@Inject(method = "takeShieldHit", at = @At("HEAD"))
	private void shieldHit(LivingEntity attacker, CallbackInfo ci) {
		if ((Object) this instanceof PlayerEntity a) {
			if (attacker instanceof PlayerEntity player) {
				if (a.getMainHandStack().isOf(ItemInit.ShadowShield)
						|| a.getOffHandStack().isOf(ItemInit.ShadowShield)) {
					Random random = new Random();
					int rand = random.nextInt(10);
					CosmicVeil.LOGGER.info(String.valueOf(rand));
					if (rand == 1) {
						CosmicVeilComponents.SHIFTED.get(player).setRemainingTrapTicks(500);
					}
				}
			}
		}
	}
}
