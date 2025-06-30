package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WarturtleRenderer extends AgeableMobEntityRenderer<WarturtleEntity, WarturtleRenderState, WarturtleModel> {
    public WarturtleRenderer(EntityRendererFactory.Context context) {
        super(context, new WarturtleModel(context.getPart(ModEntityModelLayers.WARTURTLE)),
                new WarturtleModel(context.getPart(ModEntityModelLayers.WARTURTLE_BABY)), 0.9f);
        this.addFeature(new WarturtleArmorFeatureRenderer(this, context.getEntityModels(), context.getEquipmentRenderer()));
    }

    /*@Override // 1.21.1
    public void render(WarturtleRenderState renderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        //if (livingEntity.isBaby()) { // This goes into the model render method
        //    matrixStack.scale(0.5f, 0.5f, 0.5f);
        //} else {
        //    matrixStack.scale(1f, 1f, 1f);
        //}

        super.render(renderState, matrixStack, vertexConsumerProvider, i);
    }*/

    @Override
    public WarturtleRenderState createRenderState() { return new WarturtleRenderState(); }

    @Override
    public Identifier getTexture(WarturtleRenderState state) {
        return MCCourseMod.id("textures/entity/warturtle/warturtle.png");
    }

    @Override
    public void updateRenderState(WarturtleEntity warturtleEntity, WarturtleRenderState warturtleRenderState, float f) {
        super.updateRenderState(warturtleEntity, warturtleRenderState, f);
        warturtleRenderState.bodyArmor = warturtleEntity.getBodyArmor();
        warturtleRenderState.dyeColor = warturtleEntity.getSwag();
        warturtleRenderState.hasTier1Chest = warturtleEntity.hasTier1Chest();
        warturtleRenderState.hasTier2Chest = warturtleEntity.hasTier2Chest();
        warturtleRenderState.hasTier3Chest = warturtleEntity.hasTier3Chest();
        warturtleRenderState.idleAnimationState.copyFrom(warturtleEntity.idleAnimationState);
        warturtleRenderState.sittingTransitionAnimationState.copyFrom(warturtleEntity.sittingTransitionAnimationState);
        warturtleRenderState.sittingAnimationState.copyFrom(warturtleEntity.sittingAnimationState);
        warturtleRenderState.standingTransitionAnimationState.copyFrom(warturtleEntity.standingTransitionAnimationState);
    }
}
