package com.cosmo.util;

import com.cosmo.CosmicVeilComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class CompassComponent implements AutoSyncedComponent {
    BlockPos pos = new BlockPos(0,0,0);
    PlayerEntity player;

    public CompassComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        pos = new BlockPos(
                nbtCompound.getInt("PosX"),
                nbtCompound.getInt("PosY"),
                nbtCompound.getInt("PosZ")
        );
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("PosX",pos.getX());
        nbtCompound.putInt("PosY",pos.getY());
        nbtCompound.putInt("PosZ",pos.getZ());
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos blockPos) {
        pos = blockPos;
        CosmicVeilComponents.Compass.sync(player);
    }
}
