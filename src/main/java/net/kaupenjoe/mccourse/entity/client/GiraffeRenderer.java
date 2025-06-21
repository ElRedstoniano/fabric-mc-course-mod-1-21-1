package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.GiraffeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GiraffeRenderer extends MobEntityRenderer<GiraffeEntity, GiraffeModel<GiraffeEntity>> {
    public GiraffeRenderer(EntityRendererFactory.Context context) {
        super(context, new GiraffeModel<>(context.getPart(ModEntityModelLayers.GIRAFFE)), 1F);
    }

    @Override
    public Identifier getTexture(GiraffeEntity entity) {
        if (entity.isSaddled()) {
            return MCCourseMod.id("textures/entity/giraffe/giraffe_saddled.png");
        } else {
            return MCCourseMod.id("textures/entity/giraffe/giraffe.png");
        }
    }

    @Override
    public void render(GiraffeEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (livingEntity.isBaby()) {
            matrixStack.scale(0.45f, 0.55f, 0.45f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
