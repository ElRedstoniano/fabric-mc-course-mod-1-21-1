package net.kaupenjoe.mccourse.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;

public class WarturtleArmorItem extends /*ArmorItem*/Item {
    public WarturtleArmorItem(/*ArmorMaterial material, */Settings settings) {
        super(settings/*, material, EquipmentType.BODY, */);
    }



    public float[] getArmorStats(/*ItemStack stack*/) {
        float defense = 0;
        float toughness = 0;
        if (!this.getDefaultStack().isEmpty() &&
                /*this.getDefaultStack().getItem() instanceof ArmorItem*/ this.getDefaultStack().getComponents().get(DataComponentTypes.EQUIPPABLE) != null) {
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
