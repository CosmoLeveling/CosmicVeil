package com.cosmo.items;

import com.cosmo.blocks.entity.ShadowTransporterBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class ShadowTransporterCalibrator extends Item {
    public ShadowTransporterCalibrator(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();

        if (player == null) return ActionResult.PASS;

        if (player.isSneaking()) {
            // Store the clicked block's position in the item
            stack.getOrCreateNbt().put("saved_pos", NbtHelper.fromBlockPos(pos));
            stack.getOrCreateNbt().putString("saved_dim", world.getDimensionKey().getValue().toString());
            if (!world.isClient) {
                player.sendMessage(Text.of("Saved teleport target: " + pos.toShortString()), true);
            }
            return ActionResult.SUCCESS;
        } else {
            // Try applying the stored position to a TeleportBlock
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof ShadowTransporterBlockEntity teleportBE) {
                NbtCompound nbt = stack.getNbt();
                if (nbt != null && !Objects.equals(world.getDimensionKey().getValue().toString(), nbt.getString("saved_dim"))){
                    return ActionResult.SUCCESS;
                }
                if (nbt != null && nbt.contains("saved_pos")) {
                    BlockPos target = NbtHelper.toBlockPos(nbt.getCompound("saved_pos"));
                    teleportBE.setTeleportTarget(target);
                    if (!world.isClient) {
                        player.sendMessage(Text.of("Set teleport destination to " + target.toShortString()), true);
                    }
                    return ActionResult.SUCCESS;
                } else {
                    if (!world.isClient) {
                        player.sendMessage(Text.of("No teleport target saved in item."), true);
                    }
                }
            }
        }

        return ActionResult.PASS;
    }
}
