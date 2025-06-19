package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DodoRenderer extends MobEntityRenderer<DodoEntity, DodoModel> {
    public DodoRenderer(EntityRendererFactory.Context context/*, DodoModel entityModel, float shadowEntitySize*/) {
        super(context, new DodoModel(context.getPart(ModEntityModelLayers.DODO)), 0.5f);
    }

    @Override
    public Identifier getTexture(DodoEntity entity) {
        return MCCourseMod.id("textures/entity/dodo/dodo_blue.png");
    }

    @Override
    public void render(DodoEntity livingEntity, float f /*yaw*/, float g /*tickdelta*/, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
