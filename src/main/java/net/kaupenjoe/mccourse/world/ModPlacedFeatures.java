package net.kaupenjoe.mccourse.world;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> BLACKWOOD_PLACED_KEY = registerKey("blackwood_placed");
    // Mirar ejemplos de clases como OrePlacedFeatures
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        // RegistryEntryLookup<ConfiguredFeature<?, ?>>
        var configuredFeatureRegistryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(featureRegisterable, BLACKWOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACKWOOD_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f /* 1 / 0.1F = Nº que tiene que ser int*/, 2), ModBlocks.BLACKWOOD_SAPLING)

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
