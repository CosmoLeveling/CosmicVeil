package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShadowTrap extends Item {
	public ShadowTrap(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (entity instanceof PlayerEntity player) {
			if (!CosmicVeilComponents.SHIFTED.get(player).isShifted()) {
				CosmicVeilComponents.SHIFTED.get(player).setRemainingTrapTicks(1200);
				stack.decrement(1);
				return ActionResult.CONSUME;
			}
		}
		return super.useOnEntity(stack, user, entity, hand);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (!CosmicVeilComponents.SHIFTED.get(user).isShifted()&&user.isSneaking()) {
			ItemStack stack = user.getStackInHand(hand);
			CosmicVeilComponents.SHIFTED.get(user).setRemainingTrapTicks(1200);
			stack.decrement(1);
			return TypedActionResult.success(stack);
		}
		return super.use(world, user, hand);
	}


}
