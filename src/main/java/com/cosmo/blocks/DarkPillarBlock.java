package com.cosmo.blocks;

import com.cosmo.blocks.entity.DarkPillarBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DarkPillarBlock extends BlockWithEntity {
    public DarkPillarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4, 0, 4, 12, 11, 12);
    }
    public SoundEvent getAddItemSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM;
    }
    public SoundEvent getRemoveItemSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof DarkPillarBlockEntity pedestalBlockEntity){
            switch (pedestalBlockEntity.getStoredItem().getRarity()) {
                case COMMON -> {
                    return 0;
                }
                case UNCOMMON -> {
                    return 5;
                }
                case RARE -> {
                    return 10;
                }
                case EPIC -> {
                    return 15;
                }
            }
        }
        return 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof DarkPillarBlockEntity pedestalBlockEntity
                && direction == Direction.UP && pedestalBlockEntity.getStoredItem().getRarity() == Rarity.EPIC){
            return 15;
        }

        return 0;

    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getStrongRedstonePower(state, world, pos, direction);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }
    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DarkPillarBlockEntity(pos,state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player ,Hand hand,  BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        BlockPos aboveBlock = pos.up();

        if (!world.getBlockState(aboveBlock).isAir()){
            return ActionResult.FAIL;
        }

        if (blockEntity instanceof DarkPillarBlockEntity pedestalBlockEntity) {
            ItemStack playerHeldItem = player.getStackInHand(Hand.MAIN_HAND);
            ItemStack storedItem = pedestalBlockEntity.getStoredItem();
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY()+1, pos.getZ(), storedItem);

            if (storedItem.isEmpty() ) {

                pedestalBlockEntity.setStoredItem(playerHeldItem.split(1));

                world.playSound(null, pos, getAddItemSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                return ActionResult.SUCCESS; // Return success as we stored the item
            } else
                // If pedestal has an item, give it back to the player and clear the pedestal's item
                world.spawnEntity(itemEntity);
            world.playSound(null, pos, getRemoveItemSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            pedestalBlockEntity.setStoredItem(ItemStack.EMPTY); // Remove the item from the pedestal
            return ActionResult.SUCCESS; // Return success as we gave the item back
        }
        return ActionResult.PASS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof DarkPillarBlockEntity pedestalBlockEntity) {
            ItemStack storedItem = pedestalBlockEntity.getStoredItem();

            if (!storedItem.isEmpty()) {
                // Manually drop the stored item
                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY()+1, pos.getZ(), storedItem);
                world.spawnEntity(itemEntity);  // Spawn the item in the world
                pedestalBlockEntity.setStoredItem(ItemStack.EMPTY);  // Clear the stored item after dropping
            }
        }
        super.onBreak(world, pos, state, player);
    }

}
