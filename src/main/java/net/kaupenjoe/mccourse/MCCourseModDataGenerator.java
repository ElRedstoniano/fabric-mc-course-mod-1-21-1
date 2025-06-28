package net.kaupenjoe.mccourse;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.kaupenjoe.mccourse.datagen.*;
import net.kaupenjoe.mccourse.enchantment.ModEnchantments;
import net.kaupenjoe.mccourse.trim.ModTrimMaterials;
import net.kaupenjoe.mccourse.trim.ModTrimPatterns;
import net.kaupenjoe.mccourse.world.ModConfiguredFeatures;
import net.kaupenjoe.mccourse.world.ModPlacedFeatures;
import net.kaupenjoe.mccourse.world.biome.ModBiomes;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class MCCourseModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		FabricTagProvider.BlockTagProvider blockTagProvider = pack.addProvider(ModBlockTagProvider::new);
		//pack.addProvider(ModItemTagProvider::new);
		pack.addProvider( // Para poder usar el .copy() de los tags de los bloques a los items
				(fabricDataOutput, completableFuture) -> new ModItemTagProvider(fabricDataOutput, completableFuture, blockTagProvider));
		// https://discord.com/channels/507304429255393322/507982478276034570/1326508261180051487

		pack.addProvider(ModLootTableGenerator::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModDynamicRegistryProvider::new);
		pack.addProvider(ModSoundDefinitionProvider::new);
		pack.addProvider(ModPoiTagProvider::new);
		pack.addProvider(ModFluidTagProvider::new);
		//pack.addProvider(ModEquipmentModelProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.TRIM_MATERIAL, ModTrimMaterials::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, ModTrimPatterns::bootstrap);

		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, ModEnchantments::bootstrap);

		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiomes::bootstrap);
	}
}
