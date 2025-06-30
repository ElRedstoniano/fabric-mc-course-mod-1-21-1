package net.kaupenjoe.mccourse.trim;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.Map;

public class ModTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> FLUORITE = RegistryKey.of(RegistryKeys.TRIM_MATERIAL,
            Identifier.of(MCCourseMod.MOD_ID, "fluorite"));

    public static void bootstrap(Registerable<ArmorTrimMaterial> registerable){
        register(registerable, FLUORITE, Registries.ITEM.getEntry(ModItems.FLUORITE),
                Style.EMPTY.withColor(TextColor.parse("#b03fe0").getOrThrow()), Map.of());
    }

    private static void register(Registerable<ArmorTrimMaterial> registerable, RegistryKey<ArmorTrimMaterial> armorTrimKey, RegistryEntry<Item> item, Style style,
                                 Map<RegistryKey<EquipmentAsset>, String> overrideArmorAssets) {
        ArmorTrimMaterial trimmaterial = new ArmorTrimMaterial(armorTrimKey.getValue().getPath(), item, overrideArmorAssets,
                Text.translatable(Util.createTranslationKey("trim_material", armorTrimKey.getValue())).fillStyle(style));

        registerable.register(armorTrimKey, trimmaterial);
    }}
