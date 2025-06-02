package net.kaupenjoe.mccourse.world;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    // Mirar ejemplos de clases como OrePlacedFeatures
    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        // RegistryEntryLookup<ConfiguredFeature<?, ?>>
        var configuredFeatureRegistryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
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
    public static RegistryEntry<PlacedFeature> register /* create entry */(RegistryEntry<ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return RegistryEntry.of(new PlacedFeature(feature, List.of(modifiers)));
    }
}
