package com.cosmo.entity.custom;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.init.EntityInit;
import com.cosmo.init.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class TrapBullet extends PersistentProjectileEntity {
    public TrapBullet(EntityType<? extends PersistentProjectileEntity> entityType,World world) {
        super(EntityInit.TRAP_BULLET, world);
    }
    public TrapBullet(World world, LivingEntity user) {
        super(EntityInit.TRAP_BULLET, world);
        this.setOwner(user);
    }
    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ItemInit.ShadowTrap);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if(getOwner()!=null) {
            if (entityHitResult.getEntity() instanceof PlayerEntity player){
                if (!player.equals(getOwner())) {
                    CosmicVeilComponents.SHIFTED.get(player).setRemainingTrapTicks(500);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }
}
