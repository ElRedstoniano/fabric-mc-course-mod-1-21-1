package net.kaupenjoe.mccourse.entity.client;

import net.kaupenjoe.mccourse.entity.variant.DodoVariant;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class DodoRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public DodoVariant variant;
}
