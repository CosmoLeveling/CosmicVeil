package com.cosmo.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class TeleportUtils {

    private static final Random RANDOM = new Random();

    public static boolean teleportPlayerToDimension(ServerPlayerEntity player, MinecraftServer server, RegistryKey<World> targetDimension) {
        ServerWorld targetWorld = server.getWorld(targetDimension);
        if (targetWorld == null) return false;

        BlockPos origin = player.getBlockPos();

        // Random offset within 100 blocks
        int dx = RANDOM.nextInt(201) - 100;
        int dz = RANDOM.nextInt(201) - 100;
        BlockPos candidatePos = origin.add(dx, 0, dz);

        // Pick a random Y within world bounds
        int minY = targetWorld.getBottomY() + 1;
        int maxY = targetWorld.getTopY() - 2; // minus 2 to leave room for player
        int randomY = minY + RANDOM.nextInt(maxY - minY + 1);
        candidatePos = candidatePos.withY(randomY);

        // Find nearest safe position
        candidatePos = findSafePosition(targetWorld, candidatePos);

        // Teleport player
        if (candidatePos == null) return false;
        player.teleport(targetWorld, candidatePos.getX() + 0.5, candidatePos.getY(), candidatePos.getZ() + 0.5, player.getYaw(), player.getPitch());
        player.fallDistance = 0;
        return true;
    }

    public static BlockPos findSafePosition(ServerWorld world, BlockPos pos) {
        int minY = world.getBottomY() + 1;
        int maxY = world.getTopY() - 2; // minus 2 for player height

        // Check upwards first
        for (int y = pos.getY(); y <= maxY; y++) {
            if (isSafeAt(world, pos.withY(y))) return pos.withY(y);
        }

        // If no safe spot upwards, check downwards
        for (int y = pos.getY() - 1; y >= minY; y--) {
            if (isSafeAt(world, pos.withY(y))) return pos.withY(y);
        }

        // Fallback: return the world surface
        return null;
    }

    private static boolean isSafeAt(ServerWorld world, BlockPos pos) {
        boolean solidGround = world.getBlockState(pos.down()).isSolidBlock(world, pos.down());
        boolean spaceEmpty = world.getBlockState(pos).isAir() && world.getBlockState(pos.up()).isAir();
        return solidGround && spaceEmpty;
    }

    public static boolean teleportPlayerToDimension(ServerPlayerEntity serverPlayerEntity, MinecraftServer server, BlockPos spawnPointPosition, RegistryKey<World> registryKey) {
        ServerWorld targetWorld = server.getWorld(registryKey);
        if (targetWorld == null||spawnPointPosition == null) {
            targetWorld = server.getWorld(World.OVERWORLD);
            spawnPointPosition = server.getOverworld().getSpawnPos();
        }

        serverPlayerEntity.teleport(targetWorld, spawnPointPosition.getX() + 0.5, spawnPointPosition.getY(), spawnPointPosition.getZ() + 0.5, serverPlayerEntity.getYaw(), serverPlayerEntity.getPitch());
        serverPlayerEntity.fallDistance = 0;
        return true;
    }
}

