package net.kaupenjoe.mccourse.world;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACKWOOD_KEY = registryKey("blackwood");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        register(featureRegisterable, BLACKWOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LOG),
                new StraightTrunkPlacer(5, 6, 3),
                BlockStateProvider.of(ModBlocks.BLACKWOOD_LEAVES),
                new CherryFoliagePlacer(ConstantIntProvider.create(4), ConstantIntProvider.create(1), ConstantIntProvider.create(5),
                        0.25f, 0.5f, 0.15f, 0.05f),
                new TwoLayersFeatureSize(1, 0, 2)).dirtProvider(BlockStateProvider.of(Blocks.END_STONE)).build());
        // Mirar ejemplos de ConfiguredFeatures
    }

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
