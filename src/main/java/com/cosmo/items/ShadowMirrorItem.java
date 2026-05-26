package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShadowMirrorItem extends Item {
	public ShadowMirrorItem(Settings settings) {
		super(settings);
	}

	private boolean consumeShard(PlayerEntity player) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);
			if (stack.getItem() == Items.AMETHYST_SHARD) {
				stack.decrement(1);
				return true;
			}
		}
		return false;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if (!world.isClient) {
			ItemStack stack = player.getStackInHand(hand);
            if (CosmicVeilComponents.SHIFTED.get(player).getShiftedTimer()<=0) {
               if (consumeShard(player)) {
                    CosmicVeilComponents.SHIFTED.get(player).setShiftedTimer(5000);
                    CosmicVeilComponents.SHIFTED.get(player).toggleShifted();
                    return TypedActionResult.success(player.getStackInHand(hand));
               }
            } else {
                CosmicVeilComponents.SHIFTED.get(player).toggleShifted();
                return TypedActionResult.success(player.getStackInHand(hand));
            }
        }
		return TypedActionResult.pass(player.getStackInHand(hand));
	}
}
