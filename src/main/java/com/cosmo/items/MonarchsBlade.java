package com.cosmo.items;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.util.MonarchSwordComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonarchsBlade extends SwordItem {
    private static final String STANCE_KEY = "MonarchStance";
    public MonarchsBlade(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("Forged from the veil between light and void. Stronger in darkness."));
        int stance = stack.getOrCreateNbt().getInt(STANCE_KEY);
        tooltip.add(Text.literal("§8Current Stance: " + getStanceName(stance)));
        tooltip.add(Text.literal("§7Crouch Right-click to shift stance"));
        super.appendTooltip(stack, world, tooltip, context);
    }


    public static int getStance(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(STANCE_KEY);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int stance = stack.getOrCreateNbt().getInt(STANCE_KEY);
        if (user.isSneaking()){
        stance = (stance + 1) % 3; // 0 → 1 → 2 → 0
        stack.getOrCreateNbt().putInt(STANCE_KEY, stance);

        if (!world.isClient) {
            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);
            user.sendMessage(Text.literal("§7Stance: " + getStanceName(stance)), true);
        }
        }else{
            if (user instanceof ServerPlayerEntity player) {
                switch (stance) {
                    case 1 -> {
                        MonarchSwordComponent component = CosmicVeilComponents.MonarchSwordComponent.get(user);
                        if (!component.isLightOnCooldown()) {
//                             Dash forward a bit
                            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 1));
                            user.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 1f, 1.8f);
                            component.setLightCooldown(120);
                        }
                    }
                    case 2 -> {
                        MonarchSwordComponent component = CosmicVeilComponents.MonarchSwordComponent.get(user);
                        if (!component.isDarkOnCooldown()) {
                            // Power strike (damage boost for a short time)
                            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 80, 2));
                            user.damage(user.getDamageSources().magic(), 2f); // self cost
                            user.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 1f, 0.8f);
                            component.setDarkCooldown(140);
                        }
                    }
                    default -> {
                        MonarchSwordComponent component = CosmicVeilComponents.MonarchSwordComponent.get(user);
                        if (!component.isBalancedOnCooldown()) {
                            // Short resistance boost
                            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 1));
                            user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 1f);
                            component.setBalancedCooldown(180);
                        }
                    }
                }
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private String getStanceName(int stance) {
        return switch (stance) {
            case 1 -> "§eLight";
            case 2 -> "§5Dark";
            default -> "§7Balanced";
        };
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();

        // Darkness-based damage scaling
        float brightness = world.getLightLevel(BlockPos.ofFloored(attacker.getPos())) / 15.0F;
        float darkPower = 1.0F - brightness;

        if (!(target instanceof WeeperEntity weeper)) {
            target.damage(world.getDamageSources().mobAttack(attacker),
                    (float) (this.getAttackDamage() * (1.0F + darkPower * 0.5F)));
        } else if (attacker instanceof PlayerEntity player && !weeper.isOwner(player)) {
            target.damage(world.getDamageSources().mobAttack(attacker),
                    (float) (this.getAttackDamage() * (1.0F + darkPower * 0.5F)));
        }

        if (attacker.isSprinting() || attacker.fallDistance > 0.0F)
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.BLINDNESS, 60, 0));

        if (world.isClient)
            world.addParticle(net.minecraft.particle.ParticleTypes.SMOKE,
                    target.getX(), target.getY() + 1, target.getZ(), 0, 0.02, 0);

        return super.postHit(stack, target, attacker);
    }
}
