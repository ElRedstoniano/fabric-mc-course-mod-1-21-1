package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.GiraffeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GiraffeRenderer extends MobEntityRenderer<GiraffeEntity, GiraffeRenderState, GiraffeModel> {
    public GiraffeRenderer(EntityRendererFactory.Context context) {
        super(context, new GiraffeModel(context.getPart(ModEntityModelLayers.GIRAFFE)), 1F);
    }

    /*@Override
    public Identifier getTexture(GiraffeEntity entity) {
        if (entity.isSaddled()) {
            return MCCourseMod.id("textures/entity/giraffe/giraffe_saddled.png");
        } else {
            return MCCourseMod.id("textures/entity/giraffe/giraffe.png");
        }
    }*/

    /*@Override
    public void render(GiraffeRenderState livingEntity, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        if (livingEntity.baby) {
            matrixStack.scale(0.45f, 0.55f, 0.45f);
        }
        matrixStack.pop();
        super.render(livingEntity, matrixStack, vertexConsumerProvider, i);
    }*/

    @Override
    public Identifier getTexture(GiraffeRenderState state) {
        if (state.saddled) {
            return MCCourseMod.id("textures/entity/giraffe/giraffe_saddled.png");
        } else {
            return MCCourseMod.id("textures/entity/giraffe/giraffe.png");
        }
    }

    @Override
    public GiraffeRenderState createRenderState() {
        return new GiraffeRenderState();
    }

    @Override
    public void updateRenderState(GiraffeEntity livingEntity, GiraffeRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
    }
}
