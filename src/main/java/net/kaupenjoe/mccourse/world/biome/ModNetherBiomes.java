package net.kaupenjoe.mccourse.world.biome;

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
import net.minecraft.world.gen.feature.NetherPlacedFeatures;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.List;
import java.util.Optional;

public class ModNetherBiomes {
    private static void addFeature(GenerationSettings.LookupBackedBuilder builder, GenerationStep.Feature step, RegistryKey<PlacedFeature> feature) {
        builder.feature(step, feature);
    }

    public static Biome glowstonePlains(RegistryEntryLookup<PlacedFeature> placedFeatureGetter, RegistryEntryLookup<ConfiguredCarver<?>> carverGetter) {
        // Mob spawns
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.MONSTER, 10,new SpawnSettings.SpawnEntry(EntityType.GHAST, /*10,*/ 1, 1));

        // Biome features
        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(placedFeatureGetter, carverGetter);

        addFeature(biomeBuilder, GenerationStep.Feature.UNDERGROUND_DECORATION, NetherPlacedFeatures.GLOWSTONE_EXTRA);
        addFeature(biomeBuilder, GenerationStep.Feature.UNDERGROUND_DECORATION, NetherPlacedFeatures.GLOWSTONE);
        addFeature(biomeBuilder, GenerationStep.Feature.UNDERGROUND_DECORATION, VegetationPlacedFeatures.RED_MUSHROOM_NETHER);
        addFeature(biomeBuilder, GenerationStep.Feature.UNDERGROUND_DECORATION, OrePlacedFeatures.ORE_MAGMA);

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
                                Optional.of(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP),
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
