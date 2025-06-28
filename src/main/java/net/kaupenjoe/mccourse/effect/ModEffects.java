package net.kaupenjoe.mccourse.effect;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> SLIMEY = registerStatusEffect("slimey",
            new SlimeyEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(EntityAttributes.MOVEMENT_SPEED,
                            MCCourseMod.id("slimey"), -0.25f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );
    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, MCCourseMod.id(name), statusEffect);
    }

    public static void registerEffects(){
        MCCourseMod.LOGGER.info("Registering mod effects for " + MCCourseMod.MOD_ID);
    }
}
