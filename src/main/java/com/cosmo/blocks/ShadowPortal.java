package com.cosmo.blocks;

import com.cosmo.init.BlockInit;
import com.cosmo.util.TeleportUtils;
import com.cosmo.world.dimension.DimensionInit;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class ShadowPortal extends Block {
    private static final Map<PlayerEntity, Long> teleportCooldowns = new WeakHashMap<>();
    private static final long COOLDOWN_TICKS = 60;
    public ShadowPortal(Settings settings) {
        super(settings);
    }

    public void TeleportToDimension(World world, PlayerEntity player, BlockPos pos) {
        if (!(world instanceof ServerWorld && player.canUsePortals())) return;
        Vec3d teleportPos = player.getPos();
        RegistryKey<World> registryKey = world.getRegistryKey() == DimensionInit.SHADOW_REALM_LEVEL_KEY ? World.OVERWORLD : DimensionInit.SHADOW_REALM_LEVEL_KEY;
        if (player instanceof ServerPlayerEntity serverPlayerEntity&&registryKey.equals(World.OVERWORLD)) {ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) return;
            if (serverWorld.getBlockState(pos).isOf(BlockInit.ShadowPortal)) {
                teleportPos = pos.up().toCenterPos();
            }else{
                if (serverPlayerEntity.getSpawnPointDimension() != null && serverPlayerEntity.getSpawnPointPosition() != null) {
                    registryKey = serverPlayerEntity.getSpawnPointDimension();
                    Optional<Vec3d> position = PlayerEntity.findRespawnPosition(serverWorld, BlockPos.ofFloored(serverPlayerEntity.getSpawnPointPosition().toCenterPos()),serverPlayerEntity.getSpawnAngle(),true,player.isAlive());
                    if (position.isEmpty()) return;
                    teleportPos = position.get();
                } else {
                    teleportPos = world.getSpawnPos().toCenterPos();
                }
            }
        } else {
            ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
            if (serverWorld == null) return;
            if (serverWorld.getBlockState(pos).isOf(BlockInit.ShadowPortal)) {
                teleportPos = pos.up().toCenterPos();
            } else {
                Random random = Random.create();
                int dx = random.nextInt(201) - 100;
                int dz = random.nextInt(201) - 100;
                BlockPos candidatePos = BlockPos.ofFloored(teleportPos.add(dx, 0, dz));
                // Pick a random Y within world bounds
                int minY = serverWorld.getBottomY() + 1;
                int maxY = serverWorld.getTopY() - 2; // minus 2 to leave room for player
                int randomY = minY + random.nextInt(maxY - minY + 1);
                candidatePos = candidatePos.withY(randomY);

                // Find nearest safe position
                candidatePos = TeleportUtils.findSafePosition(serverWorld, candidatePos);
                if (candidatePos == null) return;
                teleportPos = candidatePos.toCenterPos();
            }
        }
        ServerWorld serverWorld = ((ServerWorld)world).getServer().getWorld(registryKey);
        if (serverWorld == null||teleportPos == null) return;
        FabricDimensions.teleport(player,serverWorld,new TeleportTarget(teleportPos,player.getVelocity(),player.getYaw(),player.getPitch()));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof PlayerEntity player)) return;
        if (!player.isSneaking()) return;
        if (player.getWorld().getServer()==null) return;
        long currentTime = player.getWorld().getServer().getTicks();
        long lastTeleportTime = teleportCooldowns.getOrDefault(player, 0L);
        if (currentTime - lastTeleportTime < COOLDOWN_TICKS) return;
        teleportCooldowns.put(player, currentTime);

        TeleportToDimension(world,player,pos);
    }

}
