package net.kaupenjoe.mccourse.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class GiraffeRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public boolean saddled;
}
