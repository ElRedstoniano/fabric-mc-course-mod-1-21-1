package net.kaupenjoe.mccourse.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;

public class WarturtleArmorItem extends ArmorItem {
    public WarturtleArmorItem(ArmorMaterial material, Settings settings) {
        super(material, EquipmentType.BODY, settings);
    }

    public float[] getArmorStats(/*ItemStack stack*/) {
        float defense = 0;
        float toughness = 0;
        if (!this.getDefaultStack().isEmpty() && this.getDefaultStack().getItem() instanceof ArmorItem) {
            AttributeModifiersComponent attributeModifierComponent = this.getDefaultStack().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            for (AttributeModifiersComponent.Entry entry : attributeModifierComponent.modifiers()) {
                RegistryKey<EntityAttribute> ARMOR = EntityAttributes.ARMOR.getKey().get();
                RegistryKey<EntityAttribute> ARMOR_TOUGHNESS = EntityAttributes.ARMOR_TOUGHNESS.getKey().get();
                if (entry.attribute().matchesKey(ARMOR)) {
                    defense = (float) entry.modifier().value();
                }
                if (entry.attribute().matchesKey(ARMOR_TOUGHNESS)) {
                    toughness = (float) entry.modifier().value();
                }
            }
        }

        return new float[] {defense, toughness};
    }
}
