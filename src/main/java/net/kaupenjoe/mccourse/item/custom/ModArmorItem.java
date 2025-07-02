package net.kaupenjoe.mccourse.item.custom;

import net.kaupenjoe.mccourse.item.ModArmorMaterials;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends /*ArmorItem*/ Item{
    // Copy from course https://github.com/Tutorials-By-Kaupenjoe/Fabric-Course-121-Module-2/blob/main/src/main/java/net/kaupenjoe/mccourse/item/custom/ModArmorItem.java
    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL,
                            List.of(new StatusEffectInstance(StatusEffects.HASTE, 400, 1, false, false),
                                    new StatusEffectInstance(StatusEffects.JUMP_BOOST, 400, 1, false, false))).build();

    public ModArmorItem(Settings settings) {
        //super(material, type, settings);
        super(settings);
    }

    @Override
    //public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player) {
                if(hasFullSuitOfArmorOn(player)) {
                    evaluateArmorEffects(player);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, List<StatusEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            List<StatusEffectInstance> mapStatusEffects = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffects);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial mapArmorMaterial, List<StatusEffectInstance> mapStatusEffect) {
        boolean hasPlayerEffect =
                mapStatusEffect.stream().allMatch(statusEffectInstance -> player.hasStatusEffect(statusEffectInstance.getEffectType()));
        // Si el jugador ya tiene los efectos que se quieren aplicar, retorna true

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            for (StatusEffectInstance instance : mapStatusEffect) {
                player.addStatusEffect(new StatusEffectInstance(instance.getEffectType(),
                        instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.shouldShowParticles()));
            }
        }
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        //ItemStack boots = player.getInventory().getArmorStack(0); // 1.21.4<
        //ItemStack boots = player.getInventory().getStack(EquipmentSlot.FEET.getIndex()); // another way 1.21.5+
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET); // 1.21.5+
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS); // 1.21.5+
        ItemStack breastplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player) {
        /*for (ItemStack armorStack: player.getInventory().armor) { // No longer needed in 1.21.5 as it cannot be casted to ArmorItem
            if(!(armorStack.getItem() instanceof ArmorItem armoritem)) {                                    // (they no longer extist)
                return false;
            }
        }*/

        //ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack breastplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        /*return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;*/
        EquippableComponent equippableComponentBoots = boots.getItem().getComponents().get(DataComponentTypes.EQUIPPABLE);
        EquippableComponent equippableComponentLeggings = leggings.getItem().getComponents().get(DataComponentTypes.EQUIPPABLE);
        EquippableComponent equippableComponentBreastplate = breastplate.getItem().getComponents().get(DataComponentTypes.EQUIPPABLE);
        EquippableComponent equippableComponentHelmet = helmet.getItem().getComponents().get(DataComponentTypes.EQUIPPABLE);

        return equippableComponentBoots.assetId().get().getRegistry().equals(material.assetId().getRegistry()) && equippableComponentLeggings.assetId().get().equals(material.assetId())
                && equippableComponentBreastplate.assetId().get().equals(material.assetId()) && equippableComponentHelmet.assetId().get().equals(material.assetId());
        //return !boots.isEmpty() && !leggings.isEmpty() && !breastplate.isEmpty() && !helmet.isEmpty();
    }
}
