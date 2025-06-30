package net.kaupenjoe.mccourse.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

public class WarturtleRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sittingTransitionAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standingTransitionAnimationState = new AnimationState();

    public boolean hasTier1Chest = false;
    public boolean hasTier2Chest = false;
    public boolean hasTier3Chest = false;

    public ItemStack bodyArmor = ItemStack.EMPTY;
    public DyeColor dyeColor = DyeColor.WHITE;

}
