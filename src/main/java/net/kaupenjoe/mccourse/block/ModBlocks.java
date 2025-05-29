package net.kaupenjoe.mccourse.block;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.custom.FluoriteLampBlock;
import net.kaupenjoe.mccourse.block.custom.MagicBlock;
import net.kaupenjoe.mccourse.block.custom.StrawberryCropBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block FLUORITE_BLOCK = registerBlock("fluorite_block",
            new Block(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                    .strength(4f).requiresTool()));
    public static final Block FLUORITE_ORE = registerBlock("fluorite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(2,4),
                    AbstractBlock.Settings.create().mapColor(Blocks.BLUE_ORCHID.getDefaultMapColor())
                            .strength(4f).requiresTool()));
    public static final Block RAW_FLUORITE_BLOCK = registerBlock("raw_fluorite_block",
            new Block(AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
                    .mapColor(Blocks.DIAMOND_BLOCK.getDefaultMapColor())
                            .strength(4f).requiresTool()));
    public static final Block FLUORITE_DEEPSLATE_ORE = registerBlock("fluorite_deepslate_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3,6),
                    AbstractBlock.Settings.create().mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor())
                            .strength(6f).requiresTool()));
    public static final Block FLUORITE_NETHER_ORE = registerBlock("fluorite_nether_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(4,6),
                    AbstractBlock.Settings.create().mapColor(Blocks.NETHER_GOLD_ORE.getDefaultMapColor())
                            .strength(3f).requiresTool()));
    public static final Block FLUORITE_END_ORE = registerBlock("fluorite_end_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(5,7),
                    AbstractBlock.Settings.create().mapColor(Blocks.BLUE_ORCHID.getDefaultMapColor())
                            .strength(5f).requiresTool()));
    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            new MagicBlock(AbstractBlock.Settings.create().strength(1f).requiresTool()));
    public static final Block FLUORITE_STAIRS = registerBlock("fluorite_stairs",
            new StairsBlock(ModBlocks.FLUORITE_BLOCK.getDefaultState(),
                    AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block FLUORITE_SLAB = registerBlock("fluorite_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block FLUORITE_BUTTON = registerBlock("fluorite_button",
            new ButtonBlock( BlockSetType.IRON, 10, AbstractBlock.Settings.copy(Blocks.STONE_BUTTON)));
    public static final Block FLUORITE_PRESSURE_PLATE = registerBlock("fluorite_pressure_plate",
            new PressurePlateBlock( BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));
    public static final Block FLUORITE_FENCE = registerBlock("fluorite_fence",
            new FenceBlock( AbstractBlock.Settings.copy(Blocks.OAK_FENCE)));
    public static final Block FLUORITE_FENCE_GATE = registerBlock("fluorite_fence_gate",
            new FenceGateBlock(  WoodType.ACACIA,AbstractBlock.Settings.copy(Blocks.WARPED_FENCE_GATE)));
    public static final Block FLUORITE_WALL = registerBlock("fluorite_wall",
            new WallBlock(  AbstractBlock.Settings.copy(Blocks.COBBLESTONE_WALL)));
    public static final Block FLUORITE_DOOR = registerBlock("fluorite_door",
            new DoorBlock( BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_DOOR).nonOpaque()));
    public static final Block FLUORITE_TRAPDOOR = registerBlock("fluorite_trapdoor",
            new TrapdoorBlock( BlockSetType.IRON, AbstractBlock.Settings.copy(Blocks.IRON_TRAPDOOR).nonOpaque()));
    public static final Block FLUORITE_LAMP = registerBlock("fluorite_lamp",
            new FluoriteLampBlock( AbstractBlock.Settings.create().strength(1f).requiresTool()
                    .luminance(state -> state.get(FluoriteLampBlock.CLICKED) ? 15 : 0)));
    public static final Block STRAWBERRY_CROP = registerBlockWithoutBlockItem("strawberry_crop",
            new StrawberryCropBlock(AbstractBlock.Settings.copy(Blocks.WHEAT)));

    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(MCCourseMod.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MCCourseMod.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM,Identifier.of(MCCourseMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        MCCourseMod.LOGGER.info("Registering Mod Blocks for " + MCCourseMod.MOD_ID);
    }

}
