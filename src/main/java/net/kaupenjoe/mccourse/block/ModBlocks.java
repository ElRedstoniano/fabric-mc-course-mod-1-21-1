package net.kaupenjoe.mccourse.block;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.custom.*;
import net.kaupenjoe.mccourse.sound.ModSounds;
import net.kaupenjoe.mccourse.world.tree.ModSaplingGenerators;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.function.Function;

public class ModBlocks {

    // Antes
    /*public static final Block FLUORITE_BLOCK = registerBlock("fluorite_block",
            new Block(AbstractBlock_.Settings.create()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                    .strength(4f).requiresTool()));*/
    //Ahora
    public static final Block FLUORITE_BLOCK = registerBlock("fluorite_block",
            properties -> new Block(properties
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                    .strength(4f).requiresTool()));
    public static final Block FLUORITE_ORE = registerBlock("fluorite_ore",
            properties -> new ExperienceDroppingBlock(UniformIntProvider.create(2,4),
                    properties.mapColor(Blocks.BLUE_ORCHID.getDefaultMapColor())
                            .strength(4f).requiresTool()));
    public static final Block RAW_FLUORITE_BLOCK = registerBlock("raw_fluorite_block",
            properties -> new Block(AbstractBlock.Settings.create().mapColor(MapColor.RAW_IRON_PINK)
                    .instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(5.0F, 6.0F)
                    .mapColor(Blocks.DIAMOND_BLOCK.getDefaultMapColor()).strength(4f).requiresTool()));
    public static final Block FLUORITE_DEEPSLATE_ORE = registerBlock("fluorite_deepslate_ore",
            properties -> new ExperienceDroppingBlock(UniformIntProvider.create(3,6),
                    properties.mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor())
                            .strength(6f).requiresTool()));
    public static final Block FLUORITE_NETHER_ORE = registerBlock("fluorite_nether_ore",
            properties -> new ExperienceDroppingBlock(UniformIntProvider.create(4,6),
                    properties.mapColor(Blocks.NETHER_GOLD_ORE.getDefaultMapColor())
                            .strength(3f).requiresTool()));
    public static final Block FLUORITE_END_ORE = registerBlock("fluorite_end_ore",
            properties -> new ExperienceDroppingBlock(UniformIntProvider.create(5,7),
                    properties.mapColor(Blocks.BLUE_ORCHID.getDefaultMapColor())
                            .strength(5f).requiresTool()));
    public static final Block MAGIC_BLOCK = registerBlock("magic_block",
            properties -> new MagicBlock(properties.strength(1f).requiresTool().sounds(ModSounds.MAGIC_BLOCK_SOUNDS)));
    public static final Block FLUORITE_STAIRS = registerBlock("fluorite_stairs",
            properties -> new StairsBlock(ModBlocks.FLUORITE_BLOCK.getDefaultState(),
                    properties.strength(2f).requiresTool()));
    public static final Block FLUORITE_SLAB = registerBlock("fluorite_slab",
            properties -> new SlabBlock(properties.strength(2f).requiresTool()));
    public static final Block FLUORITE_BUTTON = registerBlock("fluorite_button",
            properties -> new ButtonBlock( BlockSetType.IRON, 10, Blocks.createButtonSettings()));
    public static final Block FLUORITE_PRESSURE_PLATE = registerBlock("fluorite_pressure_plate",
            properties -> new PressurePlateBlock( BlockSetType.IRON,
                    AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).solid()
                            .requiresTool().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block FLUORITE_FENCE = registerBlock("fluorite_fence",
            properties -> new FenceBlock( AbstractBlock.Settings.create()
                    .mapColor(Blocks.OAK_PLANKS.getDefaultMapColor())
                    .solid()
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F)
                    .sounds(BlockSoundGroup.WOOD)
                    .burnable()));
    public static final Block FLUORITE_FENCE_GATE = registerBlock("fluorite_fence_gate",
            properties -> new FenceGateBlock(  WoodType.ACACIA,AbstractBlock.Settings.create()
                    .mapColor(Blocks.WARPED_PLANKS.getDefaultMapColor()).solid()
                    .instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F)));
    public static final Block FLUORITE_WALL = registerBlock("fluorite_wall",
            properties -> new WallBlock(  AbstractBlock.Settings.copyShallow(Blocks.COBBLESTONE).solid()));
    public static final Block FLUORITE_DOOR = registerBlock("fluorite_door",
            properties -> new DoorBlock( BlockSetType.IRON,
                    AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool()
                            .strength(5.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)
            ));
    public static final Block FLUORITE_TRAPDOOR = registerBlock("fluorite_trapdoor",
            properties -> new TrapdoorBlock( BlockSetType.IRON,
                    AbstractBlock.Settings.create().mapColor(MapColor.IRON_GRAY).requiresTool()
                            .strength(5.0F).nonOpaque().allowsSpawning(Blocks::never)));
    public static final Block FLUORITE_LAMP = registerBlock("fluorite_lamp",
            properties -> new FluoriteLampBlock( properties.strength(1f).requiresTool()
                    .luminance(state -> state.get(FluoriteLampBlock.CLICKED) ? 15 : 0)));
    public static final Block STRAWBERRY_CROP = registerBlockWithoutBlockItem("strawberry_crop",
            properties -> new StrawberryCropBlock(AbstractBlock.Settings.create()
                    .mapColor(state -> state.get(CropBlock.AGE) >= 6 ? MapColor.YELLOW : MapColor.DARK_GREEN)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.CROP)
                    .pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block DAHLIA = registerBlock("dahlia",
            properties -> new FlowerBlock(StatusEffects.INVISIBILITY, 4,
                    AbstractBlock.Settings.create()
                    .mapColor(MapColor.DARK_GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block POTTED_DAHLIA = registerBlockWithoutBlockItem("potted_dahlia",
            properties -> new FlowerPotBlock(DAHLIA, Blocks.createFlowerPotSettings()));

    public static final Block COLORED_LEAVES = registerBlock("colored_leaves",
            properties -> new LeavesBlock(Blocks.createLeavesSettings(BlockSoundGroup.GRASS)));

    // Cambio en la 1.21.2-1.21.3: ahora hay que usar el método_ registryKey() para definir una ID a cada bloque e item al definirlo
    public static final Block PEDESTAL = registerBlock("pedestal",
            properties -> new PedestalBlock(properties
                    //.registryKey(RegistryKey.of(RegistryKeys.BLOCK, MCCourseMod.id("pedestal"))) // Ya está definido en el método_ registerBlock()
                    .strength(4f).requiresTool().nonOpaque()));

    public static final Block CRYSTALLIZER = registerBlock("crystallizer",
            properties -> new CrystallizerBlock(properties.strength(2f).requiresTool()));
    public static final Block BLACKWOOD_LOG = registerBlock("blackwood_log",
            properties -> new PillarBlock(
                    Blocks.createLogSettings(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN, BlockSoundGroup.WOOD)));
    public static final Block BLACKWOOD_WOOD = registerBlock("blackwood_wood",
            properties -> new PillarBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block STRIPPED_BLACKWOOD_LOG = registerBlock("stripped_blackwood_log",
            properties -> new PillarBlock(
                    Blocks.createLogSettings(MapColor.OAK_TAN, MapColor.OAK_TAN, BlockSoundGroup.WOOD)));
    public static final Block STRIPPED_BLACKWOOD_WOOD = registerBlock("stripped_blackwood_wood",
            properties -> new PillarBlock(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F).sounds(BlockSoundGroup.WOOD).burnable()));

    public static final Block BLACKWOOD_PLANKS = registerBlock("blackwood_planks",
            properties -> new Block(AbstractBlock.Settings.create()
                    .mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).burnable()
            ));
    public static final Block BLACKWOOD_LEAVES = registerBlock("blackwood_leaves",
            properties -> new LeavesBlock(Blocks.createLeavesSettings(BlockSoundGroup.GRASS)));

    // Ya no se puede usar AbstractBlock.Settings.copy() porque también copia la ID
    public static final Block BLACKWOOD_SAPLING = registerBlock("blackwood_sapling",
            properties -> new ModSaplingBlock(ModSaplingGenerators.BLACKWOOD,
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DARK_GREEN)
                            .noCollision()
                            .ticksRandomly()
                            .breakInstantly()
                            .sounds(BlockSoundGroup.GRASS)
                            .pistonBehavior(PistonBehavior.DESTROY),
                    Blocks.END_STONE));

    public static final Block COAL_GENERATOR = registerBlock("coal_generator",
            properties -> new CoalGeneratorBlock(properties.strength(2f).requiresTool()));
    public static final Block TANK = registerBlock("tank",
            properties -> new TankBlock(properties.strength(2f).requiresTool().nonOpaque()));


    private static Block registerBlockWithoutBlockItem(String name, Function<AbstractBlock.Settings, Block> function){
        return Registry.register(Registries.BLOCK, Identifier.of(MCCourseMod.MOD_ID, name),
                function.apply(AbstractBlock.Settings.create() // De manera automática se crea un nuevo bloque y se infieren las settings a su constructor
                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK, MCCourseMod.id(name)))));
        /*new Function<AbstractBlock.Settings, Block>() {
                    @Override
                    public Block apply(AbstractBlock.Settings settings) {
                        return new Block(settings);
                    }
                }.apply(AbstractBlock.Settings.create()
                        .registryKey(RegistryKey.of(RegistryKeys.BLOCK, MCCourseMod.id(name)))));*/
        // Otra forma de definir la misma función
    }

    private static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> function){
        Block blockToRegister = function.apply(AbstractBlock.Settings.create()
                .registryKey(RegistryKey.of(RegistryKeys.BLOCK, MCCourseMod.id(name))));
        registerBlockItem(name, blockToRegister);
        return Registry.register(Registries.BLOCK, Identifier.of(MCCourseMod.MOD_ID, name), blockToRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM,Identifier.of(MCCourseMod.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useItemPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, MCCourseMod.id(name)))));
    }

    public static void registerModBlocks(){
        MCCourseMod.LOGGER.info("Registering Mod Blocks for " + MCCourseMod.MOD_ID);
    }

}
