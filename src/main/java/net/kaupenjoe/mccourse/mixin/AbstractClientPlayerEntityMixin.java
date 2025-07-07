package net.kaupenjoe.mccourse.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    public AbstractClientPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    //@Inject(method = "getFovMultiplier", at = @At(value = "TAIL"), cancellable = true)
    //private void getFovMultiplierMixin(boolean firstPerson, float fovEffectScale, CallbackInfoReturnable<Float> cir){

    @WrapOperation(method = "getFovMultiplier(ZF)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean getFovMultiplierMixin(ItemStack itemStack, Item item, Operation<Boolean> original){
        return this.getActiveItem().isOf(ModItems.KAUPEN_BOW) || original.call(itemStack, item);
    }
}
