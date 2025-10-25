package net.kaupenjoe.mccourse.block.entity.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.render.RenderLayerHelper;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.kaupenjoe.mccourse.block.entity.custom.TankBlockEntity;
import net.kaupenjoe.mccourse.block.entity.renderer.state.TankBlockEntityRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// Credits to TurtyWurtyº
// Under MIT-License: https://github.com/DaRealTurtyWurty/1.20-Tutorial-Mod?tab=MIT-1-ov-file#readme
// Major Rewrites for Fabric
public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity, TankBlockEntityRenderState> {
    public TankBlockEntityRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(TankBlockEntityRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        FluidVariant fluidStack = renderState.fluid;
        if (fluidStack.isBlank())
            return;

        World level = renderState.world;
        if (level == null)
            return;

        final Sprite sprite = FluidVariantRendering.getSprite(fluidStack);
        int color = FluidVariantRendering.getColor(fluidStack);
        FluidState state = fluidStack.getFluid().getDefaultState();
        //BlockState state = fluidStack.getFluid().getDefaultState().getBlockState();

        float height = (((float) renderState.fluidStorage.getAmount() / renderState.fluidStorage.getCapacity()) * 0.625f) + 0.25f;

        //VertexConsumer builder = vertexConsumers.getBuffer(RenderLayers.getFluidLayer(state)); // 1.21.5<
        //VertexConsumer builder = vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state));
        //VertexConsumer builder = vertexConsumers.getBuffer(RenderLayers.getMovingBlockLayer(state));
        //VertexConsumer builder = vertexConsumers.getBuffer(RenderLayerHelper.getMovingBlockLayer(state)); // Just testing this those ones
        //VertexConsumer builder = vertexConsumers.getBuffer(RenderLayerHelper.getMovingBlockLayer(RenderLayers.getFluidLayer(state))); // 1.21.6<

        //BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR); // Nope

        // Took a look trom LightningEntityRenderer as an example
        queue.getBatchingQueue(0).submitCustom(matrices, RenderLayerHelper.getMovingBlockLayer(RenderLayers.getFluidLayer(state)),
                (matricesEntry, builder) -> {
                    int light = getLightLevel(renderState.world, renderState.pos);
                    // Top Texture
                    drawQuad(builder, matrices, 0.1f, height, 0.1f, 0.9f, height, 0.9f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    drawQuad(builder, matrices, 0.1f, 0, 0.1f, 0.9f, height, 0.1f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    // North texture ^

                    // Bottom texture
                    matrices.push();
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
                    matrices.translate(0, 0f, -1f);
                    drawQuad(builder, matrices, 0.1f, -0.01f, 0.1f, 0.9f, -0.01f, 0.9f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    matrices.pop();

                    // South texture
                    matrices.push();
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                    matrices.translate(-1f, 0, -1.8f);
                    drawQuad(builder, matrices, 0.1f, 0, 0.9f, 0.9f, height, 0.9f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    matrices.pop();

                    // West texture
                    matrices.push();
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                    matrices.translate(-1f, 0, 0);
                    drawQuad(builder, matrices, 0.1f, 0, 0.1f, 0.9f, height, 0.1f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    matrices.pop();

                    // East
                    matrices.push();
                    matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(90));
                    matrices.translate(0, 0, -1f);
                    drawQuad(builder, matrices, 0.1f, 0, 0.1f, 0.9f, height, 0.1f, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), light, color);
                    matrices.pop();
                });
    }

    @Override
    public void updateRenderState(TankBlockEntity blockEntity, TankBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
        state.fluid = blockEntity.getFluid();
        state.world = blockEntity.getWorld();
        state.pos = blockEntity.getPos();
    }

    //@Override // 1.21.6<
    //public void render(TankBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {}

    private static void drawVertex(VertexConsumer builder, MatrixStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
        builder.vertex(poseStack.peek().copy(), x, y, z)
                .color(color)
                .texture(u, v)
                .light(packedLight)
                .normal(1, 0, 0);
    }

    private static void drawQuad(VertexConsumer builder, MatrixStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
        drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
        drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
        drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
    }

    @Override
    public TankBlockEntityRenderState createRenderState() {
        return new TankBlockEntityRenderState();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight,sLight);
    }
}