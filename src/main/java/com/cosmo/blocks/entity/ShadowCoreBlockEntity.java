package com.cosmo.blocks.entity;

import com.cosmo.blocks.ShadowCoreBlock;
import com.cosmo.init.BlockEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.RenderUtils;

public class ShadowCoreBlockEntity extends BlockEntity implements GeoBlockEntity, TickableBlockEntity, ImplementedInventory{
    private AnimatableInstanceCache instanceCache = new SingletonAnimatableInstanceCache(this);
    private static final RawAnimation Grab_0 = RawAnimation.begin().thenPlay("Grab_0").thenLoop("idle");
    private static final RawAnimation Grab_1 = RawAnimation.begin().thenPlay("Grab_1").thenLoop("idle");
    private static final RawAnimation Grab_2 = RawAnimation.begin().thenPlay("Grab_2").thenLoop("idle");
    private static final RawAnimation Grab_3 = RawAnimation.begin().thenPlay("Grab_3").thenLoop("idle");
    private static final RawAnimation Grab_4 = RawAnimation.begin().thenPlay("Grab_4").thenLoop("idle");
    private static final RawAnimation Grab_5 = RawAnimation.begin().thenPlay("Grab_5").thenLoop("idle");
    private static final RawAnimation Grab_6 = RawAnimation.begin().thenPlay("Grab_6").thenLoop("idle");
    private static final RawAnimation Grab_7 = RawAnimation.begin().thenPlay("Grab_7").thenLoop("idle");
    private static final RawAnimation Idle = RawAnimation.begin().thenLoop("idle");

    private int State;
    private int AnimTick;
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(9,ItemStack.EMPTY);
    public ShadowCoreBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ShadowCoreBlockEntityType, pos, state);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
    public void sync() {
        if (world != null && !world.isClient) {
            markDirty();
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,items);
        nbt.putInt("state", this.State);
        nbt.putInt("animtick", this.AnimTick);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,items);
        this.State = nbt.contains("state", NbtElement.INT_TYPE) ? nbt.getInt("state") : 0;
        this.AnimTick = nbt.contains("animtick", NbtElement.INT_TYPE) ? nbt.getInt("animtick") : 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this,
                (state) -> {
                    int State = state.getAnimatable().getState();
                    if (State == 1) {
                        return state.setAndContinue(Grab_0);
                    } else if (State == 2) {
                        return state.setAndContinue(Grab_1);
                    } else if (State == 3) {
                        return state.setAndContinue(Grab_2);
                    } else if (State == 4) {
                        return state.setAndContinue(Grab_3);
                    } else if (State == 5) {
                        return state.setAndContinue(Grab_4);
                    } else if (State == 6) {
                        return state.setAndContinue(Grab_5);
                    } else if (State == 7) {
                        return state.setAndContinue(Grab_6);
                    } else if (State == 8) {
                        return state.setAndContinue(Grab_7);
                    } else {
                        return state.setAndContinue(Idle);
                    }
        }
        ));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return instanceCache;
    }

    public int getState() {
        return this.State;
    }

    public void setState(int val) {
        this.State = val;
        sync();
    }
    public int getAnimTick() {
        return this.AnimTick;
    }

    public void tickAnim() {
        this.AnimTick++;
        sync();
    }
    public void resetAnimTick() {
        this.AnimTick = 0;
        sync();
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void tick() {
        if (this.getWorld().getBlockState(pos).getBlock() instanceof ShadowCoreBlock shadowCoreBlock){
            if (!shadowCoreBlock.validAltar(world,pos)){
                setState(0);
                    DefaultedList<ItemStack> storedItem = this.getItems();
                    storedItem.set(8,ItemStack.EMPTY);
                    if (!storedItem.isEmpty()) {
                        for (ItemStack items :storedItem) {
                            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), items);
                            world.spawnEntity(itemEntity);
                        }
                        storedItem.clear();
                    }
            }
        }
        if (getState()==0) {
            resetAnimTick();
        }else{
            if (getAnimTick()<35) {
                if(getAnimTick()==20){
                    BlockPos pos1 = ShadowCoreBlock.posList.get(getState()-1);
                    BlockEntity entity = getWorld().getBlockEntity(pos.add(pos1));
                    if (entity instanceof DarkPillarBlockEntity pillarBlock&&pillarBlock.hasStoredItem()) {
                        setStack(getState()-1,pillarBlock.getStoredItem());
                        pillarBlock.removeStoredItem();
                    }else{
                        setState(0);
                        DefaultedList<ItemStack> storedItem = this.getItems();
                        storedItem.set(8,ItemStack.EMPTY);
                        if (!storedItem.isEmpty()) {
                            for (ItemStack items :storedItem) {
                                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), items);
                                world.spawnEntity(itemEntity);
                            }
                            storedItem.clear();
                            return;
                        }
                    }
                }
                tickAnim();
            }else {
                switch (getState()){
                    case 1:
                        setState(2);
                        resetAnimTick();
                        break;
                    case 2:
                        setState(3);
                        resetAnimTick();
                        break;
                    case 3:
                        setState(4);
                        resetAnimTick();
                        break;
                    case 4:
                        setState(5);
                        resetAnimTick();
                        break;
                    case 5:
                        setState(6);
                        resetAnimTick();
                        break;
                    case 6:
                        setState(7);
                        resetAnimTick();
                        break;
                    case 7:
                        setState(8);
                        resetAnimTick();
                        break;
                    case 8:
                        setState(0);
                        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), getItems().get(8));
                        world.spawnEntity(itemEntity);
                        items.clear();
                        resetAnimTick();
                        break;
                }
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }
}
