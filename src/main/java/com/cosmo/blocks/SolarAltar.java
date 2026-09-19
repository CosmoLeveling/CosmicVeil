package com.cosmo.blocks;

import com.cosmo.init.BlockInit;
import com.cosmo.world.dimension.DimensionInit;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Map;
import java.util.WeakHashMap;

public class SolarAltar extends Block {
    private static final Map<PlayerEntity, Long> teleportCooldowns = new WeakHashMap<>();
    private static final long COOLDOWN_TICKS = 60;

    public SolarAltar(Settings settings) {
        super(settings);
    }

    public void TeleportToDimension(World world, PlayerEntity player, BlockPos pos) {
        if (!(world instanceof ServerWorld && player.canUsePortals())) return;
        Vec3d teleportPos = player.getPos();
        RegistryKey<World> registryKey = world.getRegistryKey() == DimensionInit.SOLAR_REALM_LEVEL_KEY ? World.OVERWORLD : DimensionInit.SOLAR_REALM_LEVEL_KEY;
        if (player instanceof ServerPlayerEntity serverPlayerEntity && registryKey.equals(World.OVERWORLD)) {
            ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
        } else if (world.getBlockState(pos.north()).getBlock() == Blocks.MAGMA_BLOCK &&
                world.getBlockState(pos.south()).getBlock() == Blocks.MAGMA_BLOCK &&
                world.getBlockState(pos.east()).getBlock() == Blocks.MAGMA_BLOCK &&
                world.getBlockState(pos.west()).getBlock() == Blocks.MAGMA_BLOCK &&
                world.getBlockState(pos.north().east()).getBlock() == Blocks.LAVA &&
                world.getBlockState(pos.north().west()).getBlock() == Blocks.LAVA &&
                world.getBlockState(pos.south().east()).getBlock() == Blocks.LAVA &&
                world.getBlockState(pos.south().west()).getBlock() == Blocks.LAVA) {

            ServerWorld serverWorld = ((ServerWorld) world).getServer().getWorld(registryKey);
            if (serverWorld == null) return;
            if (serverWorld.getBlockState(pos).isOf(BlockInit.SolarAltar)) {
                teleportPos = pos.up().toCenterPos();
            } else {
                teleportPos = new Vec3d(15.5, 104, 15.5);
            }
            if (teleportPos == null) return;
            FabricDimensions.teleport(player, serverWorld, new TeleportTarget(teleportPos, player.getVelocity(), player.getYaw(), player.getPitch()));
        }

        }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.isSneaking()) return;
        if (player.getWorld().getServer() == null) return;
        long currentTime = player.getWorld().getServer().getTicks();
        long lastTeleportTime = teleportCooldowns.getOrDefault(player, 0L);
        if (currentTime - lastTeleportTime < COOLDOWN_TICKS) return;
        teleportCooldowns.put(player, currentTime);

        TeleportToDimension(world, player, pos);
    }
}
