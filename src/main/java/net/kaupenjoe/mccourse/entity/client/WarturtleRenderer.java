package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.custom.WarturtleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WarturtleRenderer extends MobEntityRenderer<WarturtleEntity, WarturtleModel<WarturtleEntity>> {
    public WarturtleRenderer(EntityRendererFactory.Context context) {
        super(context, new WarturtleModel<>(context.getPart(ModEntityModelLayers.WARTURTLE)), 0.9f);
        this.addFeature(new WarturtleArmorFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(WarturtleEntity entity) {
        return MCCourseMod.id("textures/entity/warturtle/warturtle.png");
    }

    @Override
    public void render(WarturtleEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        /*if (livingEntity.isBaby()) { // This goes into the model render method
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }*/

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
