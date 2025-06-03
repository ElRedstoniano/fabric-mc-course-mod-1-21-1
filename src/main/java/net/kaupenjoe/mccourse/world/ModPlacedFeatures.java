package net.kaupenjoe.mccourse.world;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> BLACKWOOD_PLACED_KEY = registerKey("blackwood_placed");
    public static final RegistryKey<PlacedFeature> FLUORITE_ORE_PLACED_KEY = registerKey("fluorite_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_FLUORITE_ORE_PLACED_KEY = registerKey("nether_fluorite_ore_placed");
    public static final RegistryKey<PlacedFeature> END_FLUORITE_ORE_PLACED_KEY = registerKey("end_fluorite_ore_placed");

    public static final RegistryKey<PlacedFeature> DAHLIA_PLACED_KEY = registerKey("dahlia_placed");
    // Mirar ejemplos de clases como OrePlacedFeatures
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        // RegistryEntryLookup<ConfiguredFeature<?, ?>>
        var configuredFeatureRegistryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(featureRegisterable, BLACKWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACKWOOD_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f /* 1 / 0.1F = Nº que tiene que ser int*/, 2), ModBlocks.BLACKWOOD_SAPLING)

        );
        register(featureRegisterable, FLUORITE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.FLUORITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(14, // Veins per chunk
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80)))
        );
        register(featureRegisterable, NETHER_FLUORITE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_FLUORITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(14, HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80)))
        );
        register(featureRegisterable, END_FLUORITE_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_FLUORITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(14, HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80)))
        );

        register(featureRegisterable, DAHLIA_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DAHLIA_KEY),
                RarityFilterPlacementModifier.of(4),
                SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                BiomePlacementModifier.of()
        );
    }

    // Mirar ejemplos de clases como OrePlacedFeatures
    public static RegistryKey<PlacedFeature> registerKey(String name){
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, MCCourseMod.id(name));
    }

    // Mirar clase PlacedFeatures y ejemplos
    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> modifiers
    ) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    // Mirar clase PlacedFeatures
    /*public static RegistryEntry<PlacedFeature> register // create entry //(RegistryEntry<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return RegistryEntry.of(new PlacedFeature(feature, List.of(modifiers)));
    }*/
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
