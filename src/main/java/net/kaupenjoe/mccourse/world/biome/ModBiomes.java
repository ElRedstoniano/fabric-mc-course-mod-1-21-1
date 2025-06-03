package net.kaupenjoe.mccourse.world.biome;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.world.biome.region.NetherRegion;
import net.kaupenjoe.mccourse.world.biome.region.OverworldRegion;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import terrablender.api.EndBiomeRegistry;
import terrablender.api.Regions;

public class ModBiomes {
    public static final RegistryKey<Biome> KAUPEN_VALLEY = registerBiomeKey("kaupen_valley");
    public static final RegistryKey<Biome> GLOWSTONE_PLAIN = registerBiomeKey("glowstone_plain");
    public static final RegistryKey<Biome> END_ROT = registerBiomeKey("end_rot");

    public static void registerBiomes() {
        Regions.register(new OverworldRegion(Identifier.of(MCCourseMod.MOD_ID, "mccourse_overworld"), 20));
        Regions.register(new NetherRegion(Identifier.of(MCCourseMod.MOD_ID, "mccourse_nether"), 20));

        EndBiomeRegistry.registerHighlandsBiome(END_ROT, 20);
    }

    public static void bootstrap(Registerable<Biome> context) {
        var carver = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, KAUPEN_VALLEY, ModOverworldBiomes.kaupenValley(placedFeatures, carver));
        register(context, GLOWSTONE_PLAIN, ModNetherBiomes.glowstonePlains(placedFeatures, carver));
        register(context, END_ROT, ModEndBiomes.endRot(placedFeatures, carver));
    }


    private static void register(Registerable<Biome> context, RegistryKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static RegistryKey<Biome> registerBiomeKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(MCCourseMod.MOD_ID, name));
    }
}
