package net.kaupenjoe.mccourse.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEffectSwordItem extends /*SwordItem*/ Item {
    private final RegistryEntry<StatusEffect> effect;
    public ModEffectSwordItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Settings settings, RegistryEntry<StatusEffect> effect) {
        super(toolMaterial.applySwordSettings(settings, attackDamage, attackSpeed));
        this.effect = effect;
    }

    @Override
    public /*boolean*/void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(effect, 200, 1));
        //return super.postHit(stack, target, attacker);
    }
}
