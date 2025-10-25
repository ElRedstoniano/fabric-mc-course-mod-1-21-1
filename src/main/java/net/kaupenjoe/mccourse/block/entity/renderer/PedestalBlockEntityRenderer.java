package net.kaupenjoe.mccourse.block.entity.renderer;

import it.unimi.dsi.fastutil.HashCommon;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.block.entity.renderer.state.PedestalBlockEntityRenderState;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity, PedestalBlockEntityRenderState> {
    private final ItemModelManager itemModelManager;

    public PedestalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        itemModelManager = ctx.itemModelManager();
    }

    /*@Override
    public void render(PedestalBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
    }*/

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight,sLight);
    }

    @Override
    public PedestalBlockEntityRenderState createRenderState() {
        return new PedestalBlockEntityRenderState();
    }

    @Override
    public void updateRenderState(PedestalBlockEntity blockEntity, PedestalBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        ItemStack stack = blockEntity.getStack(0);
        state.stack = stack;
        state.renderingLevitationOffset = blockEntity.getRenderingLevitationOffset(tickProgress);
        state.renderingRotation = blockEntity.getRenderingRotation(tickProgress);
        state.world = blockEntity.getWorld();
        state.pos = blockEntity.getPos();

        // Look at class ShelfBlockEntityRenderer for the example
        int i = HashCommon.long2int(blockEntity.getPos().asLong());
        //if (!stack.isEmpty()) {
            ItemRenderState itemRenderState = new ItemRenderState();
            this.itemModelManager.clearAndUpdate(itemRenderState, stack, ItemDisplayContext.GUI, blockEntity.getEntityWorld(), blockEntity, i);
            state.itemRenderState = itemRenderState;
        //}

        //boolean bl3 = MinecraftClient.getInstance().hasOutline(entity);
        //state.outlineColor = bl3 ? ColorHelper.fullAlpha(entity.getTeamColorValue()) : 0;
        state.outlineColor = 0;
    }

    @Override
    public void render(PedestalBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push(); // Pushes a new stack on the MatrixStack
        // A partir de aquí los cambios que se hagan irán a la MatrixStack, luego para terminar con los cambios se hace un .pop()
        matrices.translate(0.5f, 1.15f + state.renderingLevitationOffset, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.renderingRotation));

        /*ItemRenderer.renderItem(stack, ItemDisplayContext.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();*/ // 1.21.6<
        //List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(RenderLayers.getItemLayer(stack), false, stack.hasEnchantments()); // No

        state.itemRenderState.render(matrices, queue, getLightLevel(state.world, state.pos), OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }


}
