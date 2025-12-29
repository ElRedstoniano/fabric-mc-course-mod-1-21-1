package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.TomahawkProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.RotationAxis;

import java.util.List;

public class TomahawkProjectileRenderer extends EntityRenderer<TomahawkProjectileEntity, TomahawkProjectileRenderState> {
    public static final Identifier TEXTURE = MCCourseMod.id("textures/entity/tomahawk/tomahawk.png");
    protected TomahawkProjectileModel model;

    public TomahawkProjectileRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new TomahawkProjectileModel(ctx.getPart(ModEntityModelLayers.TOMAHAWK));
    }

    @Override
    public TomahawkProjectileRenderState createRenderState() {
        return new TomahawkProjectileRenderState();
    }

    @Override
    public void updateRenderState(TomahawkProjectileEntity entity, TomahawkProjectileRenderState state, float tickDelta) {
        super.updateRenderState(entity, state, tickDelta);
        state.yaw = entity.getLerpedYaw(tickDelta);
        state.pitch = entity.getLerpedPitch(tickDelta);
        state.rotation = entity.getLerpedRenderingRotation(tickDelta);
        state.inGround = entity.isInGround();
        state.enchanted = entity.isEnchanted();
        state.groundedOffset = entity.groundedOffset;
        state.shake = entity.shake - tickDelta;
        state.light = entity.getLight();
    }

    @Override
    public void render(TomahawkProjectileRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState) {
        matrices.push();

        if(!renderState.inGround) {
            //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(state.age, state.prevYaw, state.yawDegrees)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderState.yaw));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(renderState.rotation * 10f + 180));
            matrices.translate(0, -1.0f, 0);
        } else {                                                                        /*.getY() -> y() in 1.21.11*/
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(renderState.groundedOffset.y()));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(renderState.groundedOffset.x()));
            matrices.translate(0, -1.0f, 0);
        }
        /* 1.21.6<
        // Took a look from TridentEntityRenderer class
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
                vertexConsumers, this.model.getLayer(MCCourseMod.id("textures/entity/tomahawk/tomahawk.png")), false, renderState.enchanted);
        this.model.setAngles(renderState); // This is important for the "shake" effect when the proyectile impacts
        this.model.render(matrices, vertexConsumer, renderState.light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
        super.render(renderState, matrices, vertexConsumers, renderState.light);*/

        // Took a look from TridentEntityRenderer class
        List<RenderLayer> list = ItemRenderer.getGlintRenderLayers(this.model.getLayer(TEXTURE), false, renderState.enchanted);
        this.model.setAngles(renderState); // This is important for the "shake" effect when the proyectile impacts
        for (int i = 0; i < list.size(); i++) {
            queue.getBatchingQueue(i)
                    .submitModel(
                            this.model,
                            renderState,
                            matrices,
                            list.get(i),
                            renderState.light,
                            OverlayTexture.DEFAULT_UV,
                            -1,
                            null,
                            renderState.outlineColor,
                            null
                    );
        }
        matrices.pop();
        super.render(renderState, matrices, queue, cameraRenderState);
    }

    /*@Override 1.21.6<
    public void render(TomahawkProjectileRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {}*/

    //public Identifier getTexture(TomahawkProjectileRenderState renderState) { return TEXTURE; }
}
