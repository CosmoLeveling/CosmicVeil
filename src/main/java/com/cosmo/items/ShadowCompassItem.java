package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.util.ShiftedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.Objects;

public class ShadowCompassItem extends CompassItem {
    public static final String TARGET_UUID_KEY = "TargetPlayerUUID";

    public ShadowCompassItem(Settings settings) {
        super(settings);
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        if (!(entity instanceof PlayerEntity owner)) return;

        // Find the nearest player that matches a condition
        PlayerEntity closestUUID = world.getPlayers().stream()
                .filter(p -> p != owner) // not self
                .filter(p -> {
                    ShiftedComponent component = CosmicVeilComponents.SHIFTED.get(p);
                    return component.isShifted()||component.shadowImbued();
                }) // example condition
                .min(Comparator.comparingDouble((PlayerEntity p) -> p.squaredDistanceTo(owner)))
                .orElse(null);

        setTarget(owner, stack, Objects.requireNonNullElse(closestUUID, owner));
    }


    /** Save the target player's UUID in the compass */
    public static void setTarget(PlayerEntity user,ItemStack stack, PlayerEntity target) {
        CosmicVeilComponents.Compass.get(user).setPos(target.getBlockPos());
    }
}
