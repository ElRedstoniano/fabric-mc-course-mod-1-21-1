package net.kaupenjoe.mccourse.world.biome.region;

import com.mojang.datafixers.util.Pair;
import net.kaupenjoe.mccourse.world.biome.ModBiomes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class NetherRegion extends Region {
    public NetherRegion(Identifier name, int weight) {
        super(name, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>> mapper) {
        this.addBiome(mapper,
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F),
                MultiNoiseUtil.ParameterRange.of(0.0F), 0.0F, ModBiomes.GLOWSTONE_PLAIN);
    }
}