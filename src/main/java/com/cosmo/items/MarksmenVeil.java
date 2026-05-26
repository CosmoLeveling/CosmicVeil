package com.cosmo.items;

import com.cosmo.entity.custom.TrapBullet;
import com.cosmo.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MarksmenVeil extends Item {
    public MarksmenVeil(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            if (consumeTrap(user)) {
                TrapBullet trapBullet = new TrapBullet(world, user);
                trapBullet.setOwner(user);
                trapBullet.setPosition(user.getEyePos());
                trapBullet.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 5F, 1.0F);
                world.spawnEntity(trapBullet);
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.pass(itemStack);
    }
    private boolean consumeTrap(PlayerEntity player) {
        if (player.isCreative()) return true;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.getItem() == ItemInit.ShadowTrap) {
                stack.decrement(1);
                return true;
            }
        }
        return false;
    }
}
