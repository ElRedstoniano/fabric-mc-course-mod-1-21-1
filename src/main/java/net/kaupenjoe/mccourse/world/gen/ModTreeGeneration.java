package net.kaupenjoe.mccourse.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.kaupenjoe.mccourse.world.ModPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generatesTrees(){
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.BLACKWOOD_PLACED_KEY);
    }
}
