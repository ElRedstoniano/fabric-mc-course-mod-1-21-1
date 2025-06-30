package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.TomahawkProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

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
        //MCCourseMod.LOGGER.info(state.shake + "aaaa" + entity.shake);
    }

    @Override
    public void render(TomahawkProjectileRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        if(!state.inGround) {
            //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(state.age, state.prevYaw, state.yawDegrees)));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw));
            //MCCourseMod.LOGGER.info(state.prevYaw + "-" + state.yawDegrees + state.onGround);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.rotation * 10f + 180));
            matrices.translate(0, -1.0f, 0);
        } else {
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.groundedOffset.getY()));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(state.groundedOffset.getX()));
            matrices.translate(0, -1.0f, 0);
        }

        // Sacado a medias de la clase TridentEntityRenderer
        VertexConsumer vertexConsumer = ItemRenderer.getItemGlintConsumer(
                vertexConsumers, this.model.getLayer(MCCourseMod.id("textures/entity/tomahawk/tomahawk.png")), false, state.enchanted);
        this.model.setAngles(state); // This is important for the "shake" effect when the proyectile impacts
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();

        super.render(state, matrices, vertexConsumers, light);
    }

    //public Identifier getTexture(TomahawkProjectileRenderState renderState) { return TEXTURE; }
}
