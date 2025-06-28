package net.kaupenjoe.mccourse.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.AnimationState;

public class TomahawkProjectileRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public float prevYaw;
    public float rotation;
    public boolean onGround;
    public boolean enchanted;
    public Vector2f groundedOffset;
}
