package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class ShadowArmor extends ArmorItem {

    public ShadowArmor(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) return;

        // Get darkness factor
        float blockLight = world.getLightLevel(LightType.BLOCK, player.getBlockPos());
        float darknessFactor = 1.0F - (blockLight / 15.0F);

        // Helmet: Night Vision in darkness
        if (this.getSlotType() == EquipmentSlot.HEAD && player.getEquippedStack(EquipmentSlot.HEAD).getItem() instanceof ShadowArmor && darknessFactor > 0.7F) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 220, 0, false, false, false));
        }

        if (this.getSlotType() == EquipmentSlot.LEGS && player.getEquippedStack(EquipmentSlot.LEGS).getItem() instanceof ShadowArmor && darknessFactor > 0.7F) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 2, 2, false, false, false));
        }
        if (isWearingFullSet(player)) {
            spawnShadowTrail(world, player, darknessFactor);
        }
    }

    private boolean isWearingFullSet(PlayerEntity player) {
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack feet = player.getEquippedStack(EquipmentSlot.FEET);

        return head.getItem() instanceof ShadowArmor &&
               chest.getItem() instanceof ShadowArmor &&
               legs.getItem() instanceof ShadowArmor &&
               feet.getItem() instanceof ShadowArmor;
    }

    private void spawnShadowTrail(World world, PlayerEntity player, float darknessFactor) {
        if (CosmicVeilComponents.SHIFTED.get(player).isShifted()) return;
        Random random = world.random;
        int particleCount = (int) (2 + darknessFactor * 3); // more in darker areas
        for (int i = 0; i < particleCount; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 0.6;
            double offsetY = 0.1;
            double offsetZ = (random.nextDouble() - 0.5) * 0.6;
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.SMOKE,
                        player.getX() + offsetX,
                        player.getY() + offsetY,
                        player.getZ() + offsetZ,0,
                        0, 0.01, 0,1);
            }
        }
    }
}
