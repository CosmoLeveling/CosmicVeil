package com.cosmo.blocks;

import com.cosmo.init.BlockInit;
import com.cosmo.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Objects;

public class SolarVinesBlossom extends TallPlantBlock {
    public static final BooleanProperty TEST = BooleanProperty.of("test");
    public SolarVinesBlossom(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF,DoubleBlockHalf.LOWER).with(TEST,true));
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(BlockInit.SolarRock)||floor.isOf(BlockInit.DarkSolarRock)||floor.isOf(BlockInit.LightSolarRock);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(HALF)==DoubleBlockHalf.LOWER) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
        if (state.get(TEST)==false) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
        world.setBlockState(pos, state.with(TEST,false));
        if (!world.isClient()) {
            dropStack(world,pos,new ItemStack(ItemInit.SOLAR_BLOSSOM));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(TEST);
    }
}
