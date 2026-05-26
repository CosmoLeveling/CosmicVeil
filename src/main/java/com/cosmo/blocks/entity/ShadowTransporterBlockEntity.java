package com.cosmo.blocks.entity;

import com.cosmo.init.BlockEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ShadowTransporterBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    private BlockPos teleportTarget;

    public ShadowTransporterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.ShadowTransporterBlockEntityType, pos, state);
    }

    // Setter and getter for teleport target
    public void setTeleportTarget(BlockPos target) {
        this.teleportTarget = target;
        markDirty();
    }

    public BlockPos getTeleportTarget() {
        if (teleportTarget!=null){
            return teleportTarget;
        }else{
            return pos;
        }
    }

    // Save/load NBT so data persists
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("TeleportTarget")) {
            teleportTarget = NbtHelper.toBlockPos(nbt.getCompound("TeleportTarget"));
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (teleportTarget != null) {
            nbt.put("TeleportTarget", NbtHelper.fromBlockPos(teleportTarget));
        }
    }

    // Optional: Name & ScreenHandler for GUI (can leave empty)
    @Override
    public Text getDisplayName() {
        return Text.literal("Teleport Block");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null; // no GUI for now
    }
}
