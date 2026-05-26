package com.cosmo.blocks.entity;

import com.cosmo.init.BlockEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class DarkPillarBlockEntity extends BlockEntity implements PedestalInventory{
    private ItemStack storedItem = ItemStack.EMPTY;

    public DarkPillarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.DarkPillarBlockEntityType, pos, state);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        // Save item to NBT
        ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, storedItem)
                .result()
                .ifPresent(nbtElement -> {
                    nbt.put("StoredItem", nbtElement);
                });
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        // Load item from NBT
        storedItem = ItemStack.CODEC.parse(NbtOps.INSTANCE, nbt.get("StoredItem"))
                .result()
                .orElse(ItemStack.EMPTY);
        //System.out.println("Item loaded from NBT: " + (storedItem.isEmpty() ? "None" : storedItem.getItem().getName(storedItem)));  // Debugging line
    }

    public boolean hasStoredItem() {
        return !storedItem.isEmpty();
    }

    public void setStoredItem(ItemStack item) {
        storedItem = item;
        markDirty();  // Important to update the state
    }



    public ItemStack removeStoredItem() {
        ItemStack item = storedItem;
        storedItem = ItemStack.EMPTY;
        markDirty();
        return item;
    }


    public ItemStack getStoredItem() {
        return storedItem;  // Ensure you're returning 'storedItem', not items[0] or other values
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return DefaultedList.ofSize(1,storedItem);
    }
}
