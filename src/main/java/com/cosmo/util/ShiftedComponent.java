package com.cosmo.util;

import com.cosmo.CosmicVeil;
import com.cosmo.CosmicVeilComponents;
import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.init.BlockInit;
import com.cosmo.init.EntityInit;
import com.cosmo.init.ItemInit;
import com.cosmo.world.dimension.DimensionInit;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;

public class ShiftedComponent implements AutoSyncedComponent, ServerTickingComponent {
    private final PlayerEntity player;
    private boolean shiftToggled;
    private int remainingTrapTicks = 0;
    private int shiftedTimer = 0;
    private int weeperTimer = 0;
    private boolean shadowImbued;

    public ShiftedComponent(PlayerEntity player) {
        this.player = player;
        this.shiftToggled = false;
    }

    public boolean shouldSeeThrough() {
        return (this.shiftedTimer > 0 && this.shiftToggled) || player.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.SHADOW_HELMET)
                || EnchantmentHelper.get(player.getEquippedStack(EquipmentSlot.HEAD)).containsKey(CosmicVeil.ShadowSight) || this.remainingTrapTicks > 0 ||
                player.getWorld().getBlockState(player.getBlockPos().down()).isOf(BlockInit.ShadowTransporter) ||
                player.getWorld().getBlockState(player.getBlockPos().down()).isOf(BlockInit.ShadowPortal);
    }

    public boolean isShifted() {
        return (this.shiftedTimer > 0 && this.shiftToggled) || this.remainingTrapTicks > 0 ||
                player.getWorld().getBlockState(player.getBlockPos().down()).isOf(BlockInit.ShadowTransporter) ||
                player.getWorld().getBlockState(player.getBlockPos().down()).isOf(BlockInit.ShadowPortal);
    }

    public boolean isFullyShifted() {
        return (this.shiftedTimer > 0 && this.shiftToggled) || this.remainingTrapTicks > 0;
    }

    public static boolean shiftedCheck(Entity entity, Entity entity1) {
        if (entity instanceof PlayerEntity player1) {
            if (EnchantmentHelper.get(player1.getEquippedStack(EquipmentSlot.HEAD)).containsKey(CosmicVeil.ShadowSight) || player1.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.SHADOW_HELMET))
                return true;
            boolean inShadowRealm = player1.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY);
            boolean playerShifted = CosmicVeilComponents.SHIFTED.get(player1).isShifted();

            if (entity1 instanceof PlayerEntity player2) {
                boolean targetShifted = CosmicVeilComponents.SHIFTED.get(player2).isShifted();
                return playerShifted == targetShifted;
            }

            if (entity1 instanceof WeeperEntity weeper) {
                return playerShifted != inShadowRealm || weeper.isOwner(player1) || weeper.isForcedTarget(player1);
            }
            return !playerShifted;
        }
        if (entity instanceof WeeperEntity weeper) {
            if (!weeper.hasOwner() && !weeper.hasForcedTarget()) {
                boolean inShadowRealm = weeper.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY);

                if (entity1 instanceof PlayerEntity player) {
                    if (CosmicVeilComponents.SHIFTED.get(player).shouldSeeThrough()) return true;
                    boolean targetShifted = CosmicVeilComponents.SHIFTED.get(player).isShifted();
                    if (!inShadowRealm && targetShifted) {
                        return true;
                    } else return inShadowRealm && !targetShifted;
                }
                if (entity1 instanceof WeeperEntity weeper1) {
                    return true;
                }
                return false;
            } else {
                return true;
            }
        }
        if (entity1 instanceof PlayerEntity player) {
            return CosmicVeilComponents.SHIFTED.get(player).shouldSeeThrough() || !CosmicVeilComponents.SHIFTED.get(player).isShifted();
        }
        if (entity1 instanceof WeeperEntity weeper) {
            if (weeper.hasOwner()) {
                return true;
            }
        }
        return !(entity1 instanceof WeeperEntity);
    }

    public static boolean isPlayerShifted(PlayerEntity player) {
        return CosmicVeilComponents.SHIFTED.get(player).isShifted();
    }

    public static boolean isInShadowRealm(Entity entity) {
        return entity.getWorld().getRegistryKey().equals(DimensionInit.SHADOW_REALM_LEVEL_KEY);
    }

    public static boolean playerMatchesWeeperPlane(PlayerEntity player) {
        boolean shifted = isPlayerShifted(player);
        boolean inShadowRealm = isInShadowRealm(player);

        // (Shifted & Not In Realm)  OR  (Not Shifted & In Realm)
        return (shifted && !inShadowRealm) || (!shifted && inShadowRealm);
    }

    public static boolean playerMatchesPlayerPlane(PlayerEntity player, PlayerEntity otherPlayer) {
        boolean shifted = isPlayerShifted(player);
        boolean otherShifted = isPlayerShifted(otherPlayer);
        // (Shifted & Not In Realm)  OR  (Not Shifted & In Realm)
        return (shifted && otherShifted) || (!shifted && !otherShifted);
    }

    public static boolean playerVisibleToNormalEntity(PlayerEntity player) {
        // Normal entities only see non-shifted players
        return !isPlayerShifted(player);
    }

    public static boolean canSeePlayer(Entity viewer, Entity target) {
        if (viewer instanceof PlayerEntity player) {
            if (target instanceof WeeperEntity) {
                return playerMatchesWeeperPlane(player);
            }
            if (target instanceof PlayerEntity player1) {
                return playerMatchesPlayerPlane(player, player1);
            }
            return playerVisibleToNormalEntity(player);
        } else if (viewer instanceof WeeperEntity) {
            if (target instanceof PlayerEntity player) {
                return playerMatchesWeeperPlane(player);
            }
            return target instanceof WeeperEntity;
        }
        if (target instanceof PlayerEntity player) {
            return playerVisibleToNormalEntity(player);
        } else return !(target instanceof WeeperEntity);
    }

    public void setRemainingTrapTicks(int ticks) {
        this.weeperTimer = 200;
        this.remainingTrapTicks = ticks;
        CosmicVeilComponents.SHIFTED.sync(player);
    }

    public void toggleShifted() {
        this.weeperTimer = 200;
        this.shiftToggled = !this.shiftToggled;
        CosmicVeilComponents.SHIFTED.sync(this.player);
    }

    public void setShiftedTimer(int value) {
        this.weeperTimer = 200;
        this.shiftedTimer = value;
        CosmicVeilComponents.SHIFTED.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.remainingTrapTicks = nbtCompound.getInt("RemainingTrapTicks");
        this.shiftedTimer = nbtCompound.getInt("shiftedTimer");
        this.shiftToggled = nbtCompound.getBoolean("value");
        this.shadowImbued = nbtCompound.getBoolean("imbued");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("RemainingTrapTicks", remainingTrapTicks);
        nbtCompound.putInt("shiftedTimer", shiftedTimer);
        nbtCompound.putBoolean("value", this.shiftToggled);
        nbtCompound.putBoolean("imbued", this.shadowImbued);
    }

    @Override
    public void serverTick() {
        if (shiftToggled && !player.getInventory().contains(new ItemStack(ItemInit.ShadowMirror))) {
            shiftToggled = false;
        }

        if (isFullyShifted() && weeperTimer > 0) {
            weeperTimer--;
        } else if (!shadowImbued() && isFullyShifted() && weeperTimer <= 0) {
            if (!player.getWorld().isClient()) {
                WeeperEntity weeper = EntityInit.WEEPER.spawn((ServerWorld) player.getWorld(), player.getBlockPos(), SpawnReason.EVENT);
                player.getWorld().spawnEntity(weeper);
                if (weeper != null) {
                    weeper.setForcedTarget(player);
                }
            }
            weeperTimer = 200;
            CosmicVeilComponents.SHIFTED.sync(player);
        }
        if (shiftedTimer > 0 && shiftToggled) {
            shiftedTimer--;
            CosmicVeilComponents.SHIFTED.sync(player);
        }
        if (shiftedTimer <= 0 && shiftToggled && !player.getItemCooldownManager().isCoolingDown(ItemInit.ShadowMirror)) {
            player.getItemCooldownManager().set(ItemInit.ShadowMirror, 200);
            shiftToggled = false;
        }
        if (remainingTrapTicks > 0) {
            CosmicVeil.LOGGER.info(Integer.toString(remainingTrapTicks));
            remainingTrapTicks--;
        } else {
            remainingTrapTicks = 0;
            CosmicVeilComponents.SHIFTED.sync(player);
        }
    }

    public float getShiftedTimer() {
        return (float) shiftedTimer / 1000f;
    }

    public void setShadowImbued() {
        shadowImbued = !shadowImbued;
        CosmicVeilComponents.SHIFTED.sync(player);
    }

    public boolean shadowImbued() {
        return shadowImbued;
    }
}
