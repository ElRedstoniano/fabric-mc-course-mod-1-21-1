package net.kaupenjoe.mccourse.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class sswed extends BlockEntity implements HeldItemContext {
    public sswed(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public World getEntityWorld() {
        return null;
    }

    @Override
    public Vec3d getEntityPos() {
        return null;
    }

    @Override
    public float getBodyYaw() {
        return 0;
    }
}
