package net.kaupenjoe.mccourse.world;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACKWOOD_KEY = registryKey("blackwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FLUORITE_ORE_KEY = registryKey("fluorite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_FLUORITE_ORE_KEY = registryKey("nether_fluorite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_FLUORITE_ORE_KEY = registryKey("end_fluorite_ore");

    public static final RegistryKey<ConfiguredFeature<?, ?>> DAHLIA_KEY = registryKey("dahlia");

    public static final RegistryKey<ConfiguredFeature<?, ?>> FLUORITE_GEODE_KEY = registryKey("fluorite_geode");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        register(featureRegisterable, BLACKWOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LOG),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(1), ConstantIntProvider.create(5),
                        0.25f, 0.5f, 0.15f, 0.05f),
                new TwoLayersFeatureSize(1, 0, 2)).dirtProvider(BlockStateProvider.of(Blocks.END_STONE)).build());

        // Mirar ejemplos de ConfiguredFeatures ^^

        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldFluoriteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.FLUORITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.FLUORITE_DEEPSLATE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherFluoriteOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.FLUORITE_NETHER_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endFluoriteOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.FLUORITE_END_ORE.getDefaultState()));

        register(featureRegisterable, FLUORITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldFluoriteOres, 12));
        register(featureRegisterable, NETHER_FLUORITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherFluoriteOres, 9));
        register(featureRegisterable, END_FLUORITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(endFluoriteOres, 8));

        register(featureRegisterable, DAHLIA_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(32, 6, 2,
                PlacedFeatures.createEntry(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.DAHLIA)))));

        register(featureRegisterable, FLUORITE_GEODE_KEY, Feature.GEODE, new GeodeFeatureConfig(new GeodeLayerConfig(
                BlockStateProvider.of(Blocks.AIR),
                BlockStateProvider.of(Blocks.DEEPSLATE),
                BlockStateProvider.of(ModBlocks.FLUORITE_ORE),
                BlockStateProvider.of(Blocks.DIRT),
                BlockStateProvider.of(Blocks.EMERALD_BLOCK),
                List.of(ModBlocks.FLUORITE_BLOCK.getDefaultState()),
                BlockTags.FEATURES_CANNOT_REPLACE , BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerThicknessConfig(1.7D, 1.2D, 2.5D, 3.5D),
                new GeodeCrackConfig(0.25D, 1.5D, 1),
                0.5D, 0.1D,
                true, UniformIntProvider.create(3, 8),
                UniformIntProvider.create(2, 6), UniformIntProvider.create(1, 2),
                -16, 16, 0.075D, 1));
    }   // Mirar ejemplos de clases como ConfiguredFeatures, UndergroundConfiguredFeatures, etc... ^^

    public static RegistryKey<ConfiguredFeature<?, ?>> registryKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, MCCourseMod.id(name));
    }

    // Mirar clase ConfiguredFeature Y ConfiguredFeatureS
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ){
        registerable.register(key, new ConfiguredFeature<>(feature, config));
    }
}
