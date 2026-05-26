package com.cosmo.util;

import com.cosmo.CosmicVeilComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class MonarchSwordComponent implements AutoSyncedComponent,CommonTickingComponent {
    PlayerEntity player;
    int DarkCooldown = 0;
    int LightCooldown = 0;
    int BalancedCooldown = 0;

    public MonarchSwordComponent(PlayerEntity player){
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        DarkCooldown = tag.getInt("DarkCooldown");
        LightCooldown = tag.getInt("LightCooldown");
        BalancedCooldown = tag.getInt("BalancedCooldown");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("DarkCooldown",DarkCooldown);
        tag.putInt("LightCooldown",LightCooldown);
        tag.putInt("BalancedCooldown",BalancedCooldown);
    }

    public boolean isDarkOnCooldown() {
        return DarkCooldown>0;
    }

    public void setDarkCooldown(int i) {
        DarkCooldown = i;
        CosmicVeilComponents.MonarchSwordComponent.sync(player);
    }

    public boolean isLightOnCooldown() {
        return LightCooldown>0;
    }

    public void setLightCooldown(int i) {
        LightCooldown = i;
        CosmicVeilComponents.MonarchSwordComponent.sync(player);
    }

    public boolean isBalancedOnCooldown() {
        return BalancedCooldown>0;
    }

    public void setBalancedCooldown(int i) {
        BalancedCooldown = i;
        CosmicVeilComponents.MonarchSwordComponent.sync(player);
    }


    @Override
    public void clientTick() {
        if (this.LightCooldown>118){
            player.addVelocity(player.getRotationVector().x * 1.5, 0.1, player.getRotationVector().z * 1.5);
        }
        this.tick();
    }

    @Override
    public void tick() {
        if (isDarkOnCooldown()) {
            DarkCooldown-=1;
            if (DarkCooldown==0){
                CosmicVeilComponents.MonarchSwordComponent.sync(player);
            }
        }
        if (isLightOnCooldown()) {
            LightCooldown-=1;
            if (LightCooldown==0||LightCooldown==100){
                CosmicVeilComponents.MonarchSwordComponent.sync(player);
            }
        }
        if (isBalancedOnCooldown()) {
            BalancedCooldown-=1;
            if (BalancedCooldown==0){
                CosmicVeilComponents.MonarchSwordComponent.sync(player);
            }
        }
    }

    public float getDarkCooldown() {
        return this.DarkCooldown/120f;
    }
    public float getLightCooldown() {
        return this.LightCooldown/140f;
    }
    public float getBalancedCooldown() {
        return this.BalancedCooldown/180f;
    }
}
