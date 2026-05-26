package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShadowImbueItem extends Item {
    public ShadowImbueItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        CosmicVeilComponents.SHIFTED.get(user).setShadowImbued();
        user.getStackInHand(hand).decrement(1);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
