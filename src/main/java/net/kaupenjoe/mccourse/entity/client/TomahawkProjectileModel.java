package net.kaupenjoe.mccourse.entity.client;


import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public class TomahawkProjectileModel extends EntityModel<TomahawkProjectileRenderState> {
    private final ModelPart tomahawk;
    public TomahawkProjectileModel(ModelPart root) {
        super(root);
        this.tomahawk = root.getChild("tomahawk");
        MCCourseMod.LOGGER.info("aaaa1");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData tomahawk = modelPartData.addChild("tomahawk", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.5F, 0.0F));

        ModelPartData cube_r1 = tomahawk.addChild("cube_r1", ModelPartBuilder.create().uv(8, 7).cuboid(1.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, -4.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r2 = tomahawk.addChild("cube_r2", ModelPartBuilder.create().uv(7, 9).cuboid(0.5F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, -5.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r3 = tomahawk.addChild("cube_r3", ModelPartBuilder.create().uv(3, 10).cuboid(-2.5F, -1.5F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r4 = tomahawk.addChild("cube_r4", ModelPartBuilder.create().uv(1, 4).cuboid(-2.5F, -1.5F, 0.0F, 5.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData cube_r5 = tomahawk.addChild("cube_r5", ModelPartBuilder.create().uv(18, 1).cuboid(-0.5F, -9.0F, -0.5F, 1.0F, 18.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    /*@Override
    public void setAngles(TomahawkProjectileEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }*/

    @Override
    public void setAngles(TomahawkProjectileRenderState state) {
        super.setAngles(state);
        if (state.shake > 0.0F) { // From ArrowEntityModel class
            float f = -MathHelper.sin(state.shake * 3.0F) * state.shake;
            this.root.pitch += f * (float) (Math.PI / 180.0);
        }
    }

    /*@Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        tomahawk.render(matrices, vertexConsumer, light, overlay, color);
    }*/
}
