package net.kaupenjoe.mccourse.block.entity.renderer.state;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PedestalBlockEntityRenderState extends BlockEntityRenderState {
    public ItemStack stack;
    public float renderingLevitationOffset;
    public float renderingRotation;
    public World world;
    public BlockPos pos;
    public ItemRenderState itemRenderState;
    public int outlineColor;
}
