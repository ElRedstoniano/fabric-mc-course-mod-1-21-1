package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.client.animation.DodoAnimations;
import net.kaupenjoe.mccourse.entity.custom.DodoEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.BreezeAnimations;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class DodoModel extends SinglePartEntityModel<DodoEntity> {
    private final ModelPart body;
    private final ModelPart head;

    public DodoModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("chest").getChild("neck");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

        ModelPartData bottom_fan_r1 = body.addChild("bottom_fan_r1", ModelPartBuilder.create().uv(101, 29).cuboid(-3.5F, -7.0F, 6.0F, 7.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.5F, -1.0F, -0.464F, 0.0F, 0.0F));

        ModelPartData top_fan_r1 = body.addChild("top_fan_r1", ModelPartBuilder.create().uv(55, 25).cuboid(-5.5F, -13.0F, 4.0F, 11.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.5F, 0.0F, -0.6882F, 0.0F, 0.0F));

        ModelPartData left_fan_r1 = body.addChild("left_fan_r1", ModelPartBuilder.create().uv(45, 28).cuboid(0.0F, -14.0F, -1.0F, 0.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 4.0F, 5.0F, -0.1569F, 0.3357F, 0.0493F));

        ModelPartData right_fan_r1 = body.addChild("right_fan_r1", ModelPartBuilder.create().uv(45, 28).cuboid(0.0F, -14.0F, -1.0F, 0.0F, 14.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 4.0F, 5.0F, -0.1569F, -0.3357F, 0.05F));

        ModelPartData left_wing = body.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(5.0F, -5.0F, -5.0F));

        ModelPartData left_wing_r1 = left_wing.addChild("left_wing_r1", ModelPartBuilder.create().uv(83, 21).cuboid(1.0038F, -1.4886F, -5.0076F, 1.0F, 10.0F, 16.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0046F, 0.0879F, -0.1728F));

        ModelPartData right_wing = body.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_wing_r1 = right_wing.addChild("right_wing_r1", ModelPartBuilder.create().uv(83, 21).cuboid(-2.013F, -1.4962F, -5.0084F, 1.0F, 10.0F, 16.0F, new Dilation(0.1F)), ModelTransform.of(-5.0F, -5.0F, -5.0F, 0.0211F, -0.217F, 0.22F));

        ModelPartData main_body = body.addChild("main_body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, -3.0F));

        ModelPartData main_body_r1 = main_body.addChild("main_body_r1", ModelPartBuilder.create().uv(0, 36).cuboid(-6.0F, -5.0F, -8.0F, 12.0F, 11.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0015F, 0.0F, 0.0F));

        ModelPartData left_leg = body.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.of(3.5F, 4.0F, -1.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData left_leg_upper = left_leg.addChild("left_leg_upper", ModelPartBuilder.create().uv(60, 49).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 9.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg_lower = left_leg.addChild("left_leg_lower", ModelPartBuilder.create().uv(79, 48).cuboid(-1.5F, 0.0069F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.1F))
                .uv(79, 112).cuboid(-1.5F, 0.0069F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.35F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

        ModelPartData left_foot = left_leg_lower.addChild("left_foot", ModelPartBuilder.create(), ModelTransform.pivot(-3.5F, -11.0F, 2.5F));

        ModelPartData left_back_toe = left_foot.addChild("left_back_toe", ModelPartBuilder.create(), ModelTransform.pivot(10.5F, 24.0F, 0.0F));

        ModelPartData right_back_toe_r1 = left_back_toe.addChild("right_back_toe_r1", ModelPartBuilder.create().uv(104, 53).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, -1.0F, -3.0F, -2.7489F, 0.0F, 0.0F));

        ModelPartData left_front_toes = left_foot.addChild("left_front_toes", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_right_toe = left_front_toes.addChild("left_right_toe", ModelPartBuilder.create(), ModelTransform.pivot(3.5F, 24.0F, 0.0F));

        ModelPartData left_right_toe_r1 = left_right_toe.addChild("left_right_toe_r1", ModelPartBuilder.create().uv(92, 54).cuboid(-2.0F, -1.0F, -6.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 3.1416F, -0.0873F, -3.1416F));

        ModelPartData left_left_toe = left_front_toes.addChild("left_left_toe", ModelPartBuilder.create(), ModelTransform.pivot(3.5F, 24.0F, 0.0F));

        ModelPartData left_left_toe_r1 = left_left_toe.addChild("left_left_toe_r1", ModelPartBuilder.create().uv(92, 54).cuboid(0.0F, -1.0F, -6.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -2.0F, 3.1416F, 0.0873F, -3.1416F));

        ModelPartData right_leg = body.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.of(-3.5F, 4.0F, -1.5F, 0.0F, 3.1416F, 0.0F));

        ModelPartData right_leg_upper = right_leg.addChild("right_leg_upper", ModelPartBuilder.create().uv(60, 49).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 9.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg_lower = right_leg.addChild("right_leg_lower", ModelPartBuilder.create().uv(79, 112).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.35F))
                .uv(79, 48).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 12.0F, 3.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

        ModelPartData right_foot = right_leg_lower.addChild("right_foot", ModelPartBuilder.create(), ModelTransform.of(0.0F, 13.0F, 0.5F, 0.1745F, 0.0F, 0.0F));

        ModelPartData right_back_toe = right_foot.addChild("right_back_toe", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_back_toe_r2 = right_back_toe.addChild("right_back_toe_r2", ModelPartBuilder.create().uv(104, 53).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -1.0F, -2.9671F, 0.0F, 0.0F));

        ModelPartData right_front_toes = right_foot.addChild("right_front_toes", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData right_right_toe = right_front_toes.addChild("right_right_toe", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

        ModelPartData right_right_toe_r1 = right_right_toe.addChild("right_right_toe_r1", ModelPartBuilder.create().uv(92, 54).cuboid(-2.0F, -1.0F, -6.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 3.1416F, -0.0873F, -3.1416F));

        ModelPartData right_left_toe = right_front_toes.addChild("right_left_toe", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 1.0F));

        ModelPartData right_left_toe_r1 = right_left_toe.addChild("right_left_toe_r1", ModelPartBuilder.create().uv(92, 54).cuboid(0.0F, -1.0F, -6.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -1.0F, 3.1416F, 0.0873F, -3.1416F));

        ModelPartData chest = body.addChild("chest", ModelPartBuilder.create(), ModelTransform.of(0.0F, -11.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData lower_chest = chest.addChild("lower_chest", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.0F, 19.0F));

        ModelPartData lower_chest_r1 = lower_chest.addChild("lower_chest_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-4.0F, -12.0F, -8.5F, 8.0F, 8.0F, 10.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData neck = chest.addChild("neck", ModelPartBuilder.create(), ModelTransform.of(0.0F, 4.0F, 14.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData middle_neck = neck.addChild("middle_neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 1.0F));

        ModelPartData middle_neck_r1 = middle_neck.addChild("middle_neck_r1", ModelPartBuilder.create().uv(36, 20).cuboid(-2.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0023F, 0.0F, 0.0F));

        ModelPartData head = neck.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -9.0F, 1.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -46.0F, -8.0F, 6.0F, 6.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 40.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

        ModelPartData eye_mirrored_r1 = head.addChild("eye_mirrored_r1", ModelPartBuilder.create().uv(100, 0).mirrored().cuboid(-3.25F, -5.0F, -2.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(100, 0).cuboid(2.25F, -5.0F, -2.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData right_crest_r1 = head.addChild("right_crest_r1", ModelPartBuilder.create().uv(2, 2).cuboid(2.0F, -6.0F, 4.5F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -2.9322F, 0.0F));

        ModelPartData top_crest_r1 = head.addChild("top_crest_r1", ModelPartBuilder.create().uv(20, 0).cuboid(-2.5F, 5.0F, 5.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 2.9322F, 0.0F, 0.0F));

        ModelPartData left_crest_r1 = head.addChild("left_crest_r1", ModelPartBuilder.create().uv(2, 2).cuboid(-2.0F, -6.0F, 4.5F, 0.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 2.9322F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(DodoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform); // Es necesario para que cuando termine la animación se vuelva a reproducir y no se reproduzcan
        // todas las animaciones al mismo tiempo
        setHeadAngles(netHeadYaw, headPitch);


        this.animateMovement(DodoAnimations.ANIM_DODO_WALK,  limbSwing, limbSwingAmount, 2f, 2.5f);
        // De nuevo mirar clase CamelModel para saber de donde se sacan los números mágicos

        this.updateAnimation(entity.idleAnimationState, DodoAnimations.ANIM_DODO_IDLE, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        float anglesToRadiansFactor = ((float) Math.PI / 180);  // 180º == 1(pi) radianes => 1º == pi/180
        this.head.yaw = headYaw * anglesToRadiansFactor /* o 0.017453292F*/; // Hay que pasar los ángulos a radianes, sacado de la clase CamelEntityModel
        this.head.pitch = -headPitch * anglesToRadiansFactor;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        if (this.child) {
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.translate(0, (1 + 0.5f), 0);
        } else {
            matrices.scale(1f, 1f, 1f);
        }
        body.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return body;
    }

    /*@Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }*/
}
