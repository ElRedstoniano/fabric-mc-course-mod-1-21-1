package net.kaupenjoe.mccourse.world.biome;

import net.kaupenjoe.mccourse.world.ModPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.List;
import java.util.Optional;

public class ModOverworldBiomes {
    private static void addFeature(GenerationSettings.LookupBackedBuilder builder, GenerationStep.Feature step, RegistryKey<PlacedFeature> feature) {
        builder.feature(step, feature);
    }

    public static Biome kaupenValley(RegistryEntryLookup<PlacedFeature> placedFeatureGetter, RegistryEntryLookup<ConfiguredCarver<?>> carverGetter) {
        // Mob spawns
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        //DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder, 30); vv
        DefaultBiomeFeatures.addCaveAndMonsters(spawnBuilder, 30);
        spawnBuilder.spawn(SpawnGroup.CREATURE, 20,new SpawnSettings.SpawnEntry(EntityType.ARMADILLO,/* 20,*/ 2, 3));
        spawnBuilder.spawn(SpawnGroup.CREATURE, 10,new SpawnSettings.SpawnEntry(EntityType.CAMEL, /*10,*/ 1, 2));

        // Biome features
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(placedFeatureGetter, carverGetter);
        DefaultBiomeFeatures.addLandCarvers(biomeBuilder);
        DefaultBiomeFeatures.addMineables(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addClayDisk(biomeBuilder);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_SUGAR_CANE);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_SWAMP);

        // Custom Placed Feature
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DAHLIA_PLACED_KEY);

        /* < 1.21.10
        return new Biome.Builder()
                .precipitation(false).temperature(4.0F).downfall(0.0F)
                .effects((new BiomeEffects.Builder()).waterColor(0xbdb133).waterFogColor(0xbdb133).fogColor(0xbdb133)
                        .skyColor(getSkyColor(2.0F)).particleConfig(new BiomeParticleConfig(ParticleTypes.GLOW, 0.00725f))
                        .loopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP
                        ).moodSound(new BiomeMoodSound(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 90000, 8, 2.0D))
                        .music(MusicType.createIngameMusic(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)).build())
                .spawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();*/

        // >+ 1.21.11
        return new Biome.Builder()
                .precipitation(false)
                .temperature(4.0F)
                .downfall(0.0F)
                .effects(new BiomeEffects.Builder().waterColor(0xbdb133).build())
                .spawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build())
                .setEnvironmentAttribute(EnvironmentAttributes.WATER_FOG_COLOR_VISUAL, 0xbdb133)
                .setEnvironmentAttribute(EnvironmentAttributes.FOG_COLOR_VISUAL, 0xbdb133)
                .setEnvironmentAttribute(EnvironmentAttributes.SKY_COLOR_VISUAL, getSkyColor(2.0F))
                .setEnvironmentAttribute(EnvironmentAttributes.AMBIENT_PARTICLES_VISUAL, AmbientParticle.of(ParticleTypes.GLOW, 0.00725f))
                .setEnvironmentAttribute(
                        EnvironmentAttributes.AMBIENT_SOUNDS_AUDIO,
                        new AmbientSounds( // Took a look from TheNetherBiomeCreator class
                                Optional.of(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP),
                                Optional.of(new BiomeMoodSound(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 90000, 8, 2.0D)),
                                List.of(new BiomeAdditionsSound(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111))
                        )
                )
                .setEnvironmentAttribute(EnvironmentAttributes.BACKGROUND_MUSIC_AUDIO, new BackgroundMusic(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP))
                // Instead of .music(MusicType.createIngameMusic(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)) now its done this way ^^
                .build();
    }

    public static int getSkyColor(float temperature) {
        float f = temperature;
        f /= 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
