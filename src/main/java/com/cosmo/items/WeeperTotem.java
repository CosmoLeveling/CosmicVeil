package com.cosmo.items;

import com.cosmo.entity.custom.WeeperEntity;
import com.cosmo.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class WeeperTotem extends Item {
    public WeeperTotem(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        } else {
            if (context.getPlayer() != null) {
                ItemStack itemStack = context.getStack();
                BlockPos blockPos = context.getBlockPos();
                Direction direction = context.getSide();
                BlockState blockState = world.getBlockState(blockPos);

                BlockPos blockPos2;
                if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
                    blockPos2 = blockPos;
                } else {
                    blockPos2 = blockPos.offset(direction);
                }

                EntityType<WeeperEntity> entityType2 = EntityInit.WEEPER;
                WeeperEntity weeper = entityType2.spawnFromItemStack((ServerWorld) world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP);
                if (weeper != null) {
                    weeper.setOwner(context.getPlayer());

                    itemStack.decrement(1);
                    world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
                }

                return ActionResult.CONSUME;
            }
        }
        return ActionResult.FAIL;
    }
}
