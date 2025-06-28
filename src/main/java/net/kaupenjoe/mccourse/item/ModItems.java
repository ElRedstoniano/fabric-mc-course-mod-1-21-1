package net.kaupenjoe.mccourse.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.item.custom.*;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Function;

public class ModItems {

    public static final Item FLUORITE = registerItem("fluorite", Item::new);
    public static final Item RAW_FLUORITE = registerItem("raw_fluorite", Item::new);

    public static final Item CHAINSAW = registerItem("chainsaw",
            setting -> new ChainSawItem(setting.maxDamage(32)));
    public static final Item STRAWBERRY = registerItem("strawberry",
            setting -> new Item(
                    setting.food(ModFoodComponents.STRAWBERRY, ModFoodComponents.STRAWBERRY_EFFECT)){
                @Override
                public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                    tooltip.add(Text.translatable("tooltip.mccourse.strawberry.tooltip.1"));

                    super.appendTooltip(stack, context, tooltip, type);
                }
            });
    public static final Item STARLIGHT_ASHES = registerItem("starlight_ashes", Item::new);
    public static final Item FLUORITE_SWORD = registerItem("fluorite_sword",
            setting -> new ModEffectSwordItem(ModToolMaterials.FLUORITE,
                    3, -2.4f, setting, StatusEffects.LEVITATION));
    public static final Item FLUORITE_PICKAXE = registerItem("fluorite_pickaxe",
            setting -> new PickaxeItem(ModToolMaterials.FLUORITE, 1, -2.8f, setting));
    public static final Item FLUORITE_SHOVEL = registerItem("fluorite_shovel",
            setting -> new ShovelItem(ModToolMaterials.FLUORITE, 1.5f, -3.0f, setting));
    public static final Item FLUORITE_AXE = registerItem("fluorite_axe",
            setting -> new AxeItem(ModToolMaterials.FLUORITE, 6f, -3.2f, setting));
    public static final Item FLUORITE_HOE = registerItem("fluorite_hoe",
            setting -> new HoeItem(ModToolMaterials.FLUORITE, 0f, -3.0f, setting));
    // valores sacados de la clase vanilla de Items -> SwordItem

    public static final Item FLUORITE_PAXEL = registerItem("fluorite_paxel",
            setting -> new PaxelItem(ModToolMaterials.FLUORITE, 4, -2.5f, setting));
    public static final Item FLUORITE_HAMMER = registerItem("fluorite_hammer",
            setting -> new HammerItem(ModToolMaterials.FLUORITE, 8, -3.5f, setting));

    public static final Item FLUORITE_HELMET = registerItem("fluorite_helmet",
            setting -> new ModArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, EquipmentType.HELMET, setting
                    .maxDamage(EquipmentType.HELMET.getMaxDamage(15))));
    public static final Item FLUORITE_CHESTPLATE = registerItem("fluorite_chestplate",
            setting -> new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, EquipmentType.CHESTPLATE, setting
                    .maxDamage(EquipmentType.CHESTPLATE.getMaxDamage(15))));
    public static final Item FLUORITE_LEGGINGS = registerItem("fluorite_leggings",
            setting -> new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, EquipmentType.LEGGINGS, setting
                    .maxDamage(EquipmentType.LEGGINGS.getMaxDamage(15))));
    public static final Item FLUORITE_BOOTS = registerItem("fluorite_boots",
            setting -> new ArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, EquipmentType.BOOTS, setting
                    .maxDamage(EquipmentType.BOOTS.getMaxDamage(15))));

    public static final Item FLUORITE_HORSE_ARMOR = registerItem("fluorite_horse_armor",
            setting -> new AnimalArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, AnimalArmorItem.Type.EQUESTRIAN, setting));

    public static final Item KAUPEN_SMITHING_TEMPLATE = registerItem("kaupen_armor_trim_smithing_template", SmithingTemplateItem::of);

    public static final Item METAL_DETECTOR = registerItem("metal_detector",
            setting -> new MetalDetectorItem(setting.maxDamage(200)));
    public static final Item DATA_TABLET = registerItem("data_tablet",
            setting -> new DataTabletItem(setting.maxCount(1)));

    public static final Item KAUPEN_BOW = registerItem("kaupen_bow",
            setting -> new BowItem(setting.maxDamage(200)));

    public static final Item STRAWBERRY_SEEDS = registerItem("strawberry_seeds",
            setting -> new BlockItem(ModBlocks.STRAWBERRY_CROP, setting)); // AliasedBlockItem -> BlockItem
    // Al usarse .useItemPrefixedTranslationKey() en registerBlockItems() en ModBlocks ya no hace falta

    public static final Item BAR_BRAWL_MUSIC_DISC = registerItem("bar_brawl_music_disc",
            setting -> new Item(setting.jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).maxCount(1)));

    public static final Item SPECTRE_STAFF = registerItem("spectre_staff",
            setting -> new Item(setting.maxCount(1)));

    public static final Item DODO_SPAWN_EGG = registerItem("dodo_spawn_egg",
            setting -> new SpawnEggItem(ModEntities.DODO_ET, 0x456ae0, 0x545978, setting));

    public static final Item GIRAFFE_SPAWN_EGG = registerItem("giraffe_spawn_egg",
            setting -> new SpawnEggItem(ModEntities.GIRAFFE_ET, 0xe7d7a5, 0x7e5b41, setting));

    public static final Item TOMAHAWK = registerItem("tomahawk",
            setting -> new TomahawkItem(setting.maxCount(16)));

    public static final Item WARTURTLE_SPAWN_EGG = registerItem("warturtle_spawn_egg",
            setting -> new SpawnEggItem(ModEntities.WARTURTLE_ET, 0xa86518, 0x3b260f, setting));

    public static final Item IRON_WARTURTLE_ARMOR = registerItem("iron_warturtle_armor",
            setting -> new WarturtleArmorItem(ArmorMaterials.IRON, setting.maxDamage(200)));
    public static final Item GOLD_WARTURTLE_ARMOR = registerItem("gold_warturtle_armor",
            setting -> new WarturtleArmorItem(ArmorMaterials.GOLD, setting.maxDamage(400)));
    public static final Item DIAMOND_WARTURTLE_ARMOR = registerItem("diamond_warturtle_armor",
            setting -> new WarturtleArmorItem(ArmorMaterials.DIAMOND, setting.maxDamage(600)));
    public static final Item NETHERITE_WARTURTLE_ARMOR = registerItem("netherite_warturtle_armor",
            setting -> new WarturtleArmorItem(ArmorMaterials.NETHERITE, setting.maxDamage(800)));
    public static final Item FLUORITE_WARTURTLE_ARMOR = registerItem("fluorite_warturtle_armor",
            setting -> new WarturtleArmorItem(ModArmorMaterials.FLUORITE_ARMOR_MATERIAL, setting.maxDamage(1000)));



    private static Item registerItem(String name, Function<Item.Settings, Item> function){
        return Registry.register(Registries.ITEM,
                Identifier.of(MCCourseMod.MOD_ID, name),
                function.apply(new Item.Settings()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, MCCourseMod.id(name)))));
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
