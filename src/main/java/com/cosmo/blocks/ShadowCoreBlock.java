package com.cosmo.blocks;

import com.cosmo.CosmicVeilComponents;
import com.cosmo.blocks.entity.DarkPillarBlockEntity;
import com.cosmo.blocks.entity.ShadowCoreBlockEntity;
import com.cosmo.blocks.entity.TickableBlockEntity;
import com.cosmo.init.BlockInit;
import com.cosmo.recipe.SoulCraftingRecipe;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ShadowCoreBlock extends BlockWithEntity{
    public static List<BlockPos> posList = List.of(
            new BlockPos(-3,0,0),
            new BlockPos(-2,0,2),
            new BlockPos(0,0,3),
            new BlockPos(2,0,2),
            new BlockPos(3,0,0),
            new BlockPos(2,0,-2),
            new BlockPos(0,0,-3),
            new BlockPos(-2,0,-2)
    );
    private static BlockPos rotate(BlockPos pos, Direction facing) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        return switch (facing) {
            case WEST  -> new BlockPos( x, y,  z);
            case NORTH -> new BlockPos( z, y, -x);
            case EAST  -> new BlockPos(-x, y, -z);
            case SOUTH -> new BlockPos(-z, y,  x);
            default -> pos;
        };
    }

    public ShadowCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2,0,2,14,8,14);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShadowCoreBlockEntity(pos,state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.getTicker(world);
    }

    public boolean validAltar(World world,BlockPos pos){
        for (BlockPos position : posList) {
            if (!world.getBlockState(pos.add(position)).isOf(BlockInit.DarkPedestal)){
                return false;
            }
        }
        return true;
    }

    public ItemStack validRecipe(World world, BlockPos pos) {
        for (int rotation = 0; rotation < 4; rotation++) {
            SimpleInventory inventory = new SimpleInventory(8);
            int i = 0;

            for (int d = 0; d < posList.size(); d++) {
                int index = (d + rotation * 2) % posList.size();
                BlockPos position = posList.get(index);

                if (world.getBlockEntity(pos.add(position))
                        instanceof DarkPillarBlockEntity blockEntity) {

                    inventory.setStack(i++, blockEntity.getStoredItem());
                }
            }

            Optional<SoulCraftingRecipe> match =
                    world.getRecipeManager().getFirstMatch(
                            SoulCraftingRecipe.Type.INSTANCE,
                            inventory,
                            world
                    );

            if (match.isPresent()) {
                return match.get().getOutput();
            }
        }

        return ItemStack.EMPTY;
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ShadowCoreBlockEntity shadowCoreBlockEntity) {
            DefaultedList<ItemStack> storedItem = shadowCoreBlockEntity.getItems();
            if (!storedItem.isEmpty()) {
                storedItem.set(8,ItemStack.EMPTY);
                for (ItemStack items :storedItem) {
                    ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), items);
                    world.spawnEntity(itemEntity);
                }
                storedItem.clear();
            }
        }
        super.onBreak(world, pos, state, player);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity block = world.getBlockEntity(pos);
        ItemStack output = validRecipe(world,pos);
        if (!output.isEmpty()&& CosmicVeilComponents.SHIFTED.get(player).shadowImbued()) {
            if (block instanceof ShadowCoreBlockEntity blockEntity) {
                blockEntity.setState(1);
                blockEntity.setStack(8,output);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}
