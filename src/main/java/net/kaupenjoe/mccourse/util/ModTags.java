package net.kaupenjoe.mccourse.util;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_FLUORITE_TOOL = createTag("needs_fluorite_tool");
        public static final TagKey<Block> INCORRECT_FOR_FLUORITE_TOOL = createTag("incorrect_for_fluorite_tool");
        public static final TagKey<Block> PAXEL_MINEABLE = createTag("paxel_mineable");
        public static final TagKey<Block> FLUORITE_ORES = createTag("fluorite_ores");
        public static final TagKey<Block> METAL_DETECTOR_DETECTABLE_BLOCKS = createTag("metal_detector_detectable_blocks");

        public static final TagKey<Block> STORAGE_VALUABLE_BLOCKS = createTag("storage_valuable_blocks");
        public static final TagKey<Block> STORAGE_BLOCKS_FLUORITE_C = createCommonTag("storage_blocks/fluorite");

        public static final TagKey<Block> BLACKWOOD_LOGS = createCommonTag("blackwood_logs");
        // https://wiki.fabricmc.net/community:common_tags

        private static TagKey<Block> createTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, MCCourseMod.id(name));
        }
        private static TagKey<Block> createCommonTag(String name){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of("c", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS =
                createTag("transformable_items");
        public static final TagKey<Item> BLACKWOOD_LOGS = createCommonTag("blackwood_logs");
        public static final TagKey<Item> REPAIRS_FLUORITE_ARMOR = createCommonTag("fluorite_repair");

        private static TagKey<Item> createTag(String name){
            return TagKey.of(RegistryKeys.ITEM, MCCourseMod.id(name));
        }
        private static TagKey<Item> createCommonTag(String name){
            return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
        }
    }
}
