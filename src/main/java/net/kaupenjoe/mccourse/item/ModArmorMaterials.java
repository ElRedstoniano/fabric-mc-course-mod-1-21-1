package net.kaupenjoe.mccourse.item;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.util.ModTags;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;

public class ModArmorMaterials {
    //public static EquipmentModel FLUORITE = EquipmentModel.builder().addHumanoidLayers(MCCourseMod.id("fluorite")).build(); // No longer used here

    private static RegistryKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = RegistryKey.ofRegistry(Identifier.ofVanilla("equipment_asset"));

    public static RegistryKey<EquipmentAsset> FLUORITE_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.ofVanilla("fluorite"));

    /*public static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.ofVanilla(name));
    }*/

    public static final ArmorMaterial FLUORITE_ARMOR_MATERIAL = new ArmorMaterial(20,
            Util.make(new EnumMap(EquipmentType.class), map -> {
        map.put(EquipmentType.BOOTS, 2);
        map.put(EquipmentType.LEGGINGS, 4);
        map.put(EquipmentType.CHESTPLATE, 6);
        map.put(EquipmentType.HELMET, 2);
        map.put(EquipmentType.BODY, 4);
    }), 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, ModTags.Items.REPAIRS_FLUORITE_ARMOR, EquipmentAssetKeys.DIAMOND);
    // Mirar la clase ArmorMaterials para ver ejemplos vanilla

    /*public static final ArmorMaterial FLUORITE_ARMOR_MATERIAL = registerArmorMaterial("fluorite",
            () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class),map -> {
                        map.put(ArmorItem.Type.BOOTS, 2); // Defense values
                        map.put(ArmorItem.Type.LEGGINGS, 4);
                        map.put(ArmorItem.Type.CHESTPLATE, 6);
                        map.put(ArmorItem.Type.HELMET, 2);
                        map.put(ArmorItem.Type.BODY, 4);
                    }),
                    20,
                    SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
                    () -> Ingredient.ofItems(ModItems.FLUORITE),
                    List.of(new ArmorMaterial.Layer(Identifier.of(MCCourseMod.MOD_ID, "fluorite"))),
                    0 ,
                    0));*/
    /*public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MCCourseMod.MOD_ID, name), material.get());
    }*/
}
