package com.cosmo.blocks;

import com.cosmo.blocks.entity.ShadowTransporterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.WeakHashMap;

public class ShadowTransporter extends Block implements BlockEntityProvider {
    private static final Map<PlayerEntity, Long> teleportCooldowns = new WeakHashMap<>();
    private static final long COOLDOWN_TICKS = 60;
    public ShadowTransporter(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ShadowTransporterBlockEntity teleportBE)) {
            super.onSteppedOn(world, pos, state, entity);
            return;
        }

        if (!(entity instanceof PlayerEntity player)) return;
        if (!(player instanceof ServerPlayerEntity serverPlayerEntity)) return;

        if (world.isClient) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        long currentTime = serverPlayerEntity.getServerWorld().getServer().getTicks();
        long lastTeleportTime = teleportCooldowns.getOrDefault(serverPlayerEntity, 0L);
        if (currentTime - lastTeleportTime < COOLDOWN_TICKS) {
            return; // Still on cooldown
        }

        teleportCooldowns.put(serverPlayerEntity, currentTime);
        BlockPos targetPos = teleportBE.getTeleportTarget();

        if (pos.isWithinDistance(targetPos, 100)) {
            player.teleport(targetPos.getX() + 0.5, targetPos.getY() + 1, targetPos.getZ() + 0.5);
        } else {
            player.sendMessage(Text.literal("Distance too far"), true);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowTransporterBlockEntity(pos, state);
    }
}
