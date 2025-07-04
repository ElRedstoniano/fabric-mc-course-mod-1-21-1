package net.kaupenjoe.mccourse.enchantment;

import com.mojang.serialization.MapCodec;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.enchantment.custom.LightningStrikerEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> LIGHTNING_STRIKER = registerEntityEffect("lightning_striker", LightningStrikerEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name, MapCodec<? extends EnchantmentEntityEffect> codec){
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, MCCourseMod.id(name), codec);
    }

    public static void registerEnchantmentEffects(){
        MCCourseMod.LOGGER.info("Registering mod enchantment effects for " + MCCourseMod.MOD_ID);
    }
}
