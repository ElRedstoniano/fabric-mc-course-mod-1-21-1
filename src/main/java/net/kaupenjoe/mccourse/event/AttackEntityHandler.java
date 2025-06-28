package net.kaupenjoe.mccourse.event;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AttackEntityHandler implements AttackEntityCallback {
    @Override
    public ActionResult interact(PlayerEntity playerEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (entity instanceof SheepEntity && !world.isClient()){
            if (playerEntity.getMainHandStack().getItem() == ModItems.METAL_DETECTOR){
                playerEntity.sendMessage(Text.translatable("events.hit_metal_detector_entity", playerEntity.getName().getString()), false);
            }

            if (playerEntity.getMainHandStack().getItem() == ModItems.STRAWBERRY){
                playerEntity.sendMessage(Text.translatable("events.hit_strawberry_entity", playerEntity.getName().getString()), false);
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 400));
                playerEntity.getMainHandStack().decrement(1);
            }

            if (playerEntity.getMainHandStack().getItem() == Items.END_ROD){
                playerEntity.sendMessage(Text.translatable("events.hit_end_rod_entity", playerEntity.getName().getString()),false);
                ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 400));
                playerEntity.getMainHandStack().decrement(1);
            }
        }
        return ActionResult.PASS;
    }
}
