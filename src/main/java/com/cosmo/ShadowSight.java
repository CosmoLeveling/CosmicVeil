package com.cosmo;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class ShadowSight extends Enchantment {
    protected ShadowSight(Rarity weight) {
        super(weight, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
    }
}
