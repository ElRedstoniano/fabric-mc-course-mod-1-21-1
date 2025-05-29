package net.kaupenjoe.mccourse.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.item.custom.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {

    public static final Item FLUORITE = registerItem("fluorite", new Item(new Item.Settings()));
    public static final Item RAW_FLUORITE = registerItem("raw_fluorite", new Item(new Item.Settings()));

    public static final Item CHAINSAW = registerItem("chainsaw",
            new ChainSawItem(new Item.Settings().maxDamage(32)));
    public static final Item STRAWBERRY = registerItem("strawberry",
            new Item(new Item.Settings().food(ModFoodComponents.STRAWBERRY)){
                @Override
                public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                    tooltip.add(Text.translatable("tooltip.mccourse.strawberry.tooltip.1"));

                    super.appendTooltip(stack, context, tooltip, type);
                }
            });
    public static final Item STARLIGHT_ASHES = registerItem("starlight_ashes",new Item(new Item.Settings()));
    public static final Item FLUORITE_SWORD = registerItem("fluorite_sword",
            new ModEffectSwordItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            SwordItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 3, -2.4f)),
                    StatusEffects.LEVITATION));
    public static final Item FLUORITE_PICKAXE = registerItem("fluorite_pickaxe",
            new PickaxeItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 1, -2.8f))));
    public static final Item FLUORITE_SHOVEL = registerItem("fluorite_shovel",
            new ShovelItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            ShovelItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 1.5f, -3.0f))));
    public static final Item FLUORITE_AXE = registerItem("fluorite_axe",
            new AxeItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            AxeItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 6f, -3.2f))));
    public static final Item FLUORITE_HOE = registerItem("fluorite_hoe",
            new HoeItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            HoeItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 0f, -3.0f))));
    // valores sacados de la clase vanilla de Items -> SwordItem

    public static final Item FLUORITE_PAXEL = registerItem("fluorite_paxel",
            new PaxelItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 4, -2.5f))));
    public static final Item FLUORITE_HAMMER = registerItem("fluorite_hammer",
            new HammerItem(ModToolMaterials.FLUORITE,
                    new Item.Settings().attributeModifiers(
                            PickaxeItem.createAttributeModifiers(ModToolMaterials.FLUORITE, 8, -3.5f))));

    public static final Item FLUORITE_HELMET = registerItem("fluorite_helmet",
            new ModArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
    public static final Item FLUORITE_CHESTPLATE = registerItem("fluorite_chestplate",
            new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
    public static final Item FLUORITE_LEGGINGS = registerItem("fluorite_leggings",
            new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
    public static final Item FLUORITE_BOOTS = registerItem("fluorite_boots",
            new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

    public static final Item FLUORITE_HORSE_ARMOR = registerItem("fluorite_horse_armor",
            new AnimalArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, AnimalArmorItem.Type.EQUESTRIAN, false,
                    new Item.Settings()));

    public static final Item KAUPEN_SMITHING_TEMPLATE = registerItem("kaupen_armor_trim_smithing_template",
            SmithingTemplateItem.of(Identifier.of(MCCourseMod.MOD_ID, "kaupen"), FeatureFlags.VANILLA));

    public static final Item METAL_DETECTOR = registerItem("metal_detector",
            new MetalDetectorItem(new Item.Settings().maxDamage(200)));
    public static final Item DATA_TABLET = registerItem("data_tablet",
            new DataTabletItem(new Item.Settings().maxCount(1)));

    public static final Item KAUPEN_BOW = registerItem("kaupen_bow",
            new BowItem(new Item.Settings().maxDamage(200)));

    public static final Item STRAWBERRY_SEEDS = registerItem("strawberry_seeds",
            new AliasedBlockItem(ModBlocks.STRAWBERRY_CROP, new Item.Settings()));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM,
                Identifier.of(MCCourseMod.MOD_ID, name), item);
    }

    private static void customIngredients(FabricItemGroupEntries entries){
        entries.add(FLUORITE);
        entries.add(RAW_FLUORITE);
    }

    public static  void registerModItems(){
        MCCourseMod.LOGGER.info("Registering Mod Items for " + MCCourseMod.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::customIngredients);
    }
}
