package net.kaupenjoe.mccourse.block.entity.renderer.state;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class TankBlockEntityRenderState extends BlockEntityRenderState {
    public FluidVariant fluid;
    public World world;
    public SingleVariantStorage<FluidVariant> fluidStorage;
    public BlockPos pos = new BlockPos(Vec3i.ZERO);
}
