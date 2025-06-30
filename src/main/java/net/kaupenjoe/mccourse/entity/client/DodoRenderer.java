package net.kaupenjoe.mccourse.entity.client;

import com.google.common.collect.Maps;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.kaupenjoe.mccourse.entity.variant.DodoVariant;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class DodoRenderer extends AgeableMobEntityRenderer<DodoEntity, DodoRenderState, DodoModel> {
    private static final Map<DodoVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DodoVariant.class), map -> {
        map.put(DodoVariant.BLUE, MCCourseMod.id("textures/entity/dodo/dodo_blue.png"));
        map.put(DodoVariant.BLACK, MCCourseMod.id("textures/entity/dodo/dodo_black.png"));
    });

    public DodoRenderer(EntityRendererFactory.Context context/*, DodoModel entityModel, float shadowEntitySize*/) {
        super(context, new DodoModel(context.getPart(ModEntityModelLayers.DODO)),
                new DodoModel(context.getPart(ModEntityModelLayers.DODO_BABY)), 0.5f);
    }

    @Override
    public Identifier getTexture(DodoRenderState state) {
        //return MCCourseMod.id("textures/entity/dodo/dodo_blue.png");
        return LOCATION_BY_VARIANT.get(state.variant);
    }

    /*@Override
    public void render(DodoRenderState renderState, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        if(renderState.baby) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1,1,1);
        }
        matrixStack.pop();
        super.render(renderState, matrixStack, vertexConsumerProvider, i);
    }*/

    @Override
    public DodoRenderState createRenderState() {
        return new DodoRenderState();
    }

    @Override
    public void updateRenderState(DodoEntity livingEntity, DodoRenderState livingEntityRenderState, float f) {
        super.updateRenderState(livingEntity, livingEntityRenderState, f);
        livingEntityRenderState.idleAnimationState.copyFrom(livingEntity.idleAnimationState);
        livingEntityRenderState.variant = livingEntity.getVariant();
    }
}
