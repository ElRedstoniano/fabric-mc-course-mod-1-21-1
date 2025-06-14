package net.kaupenjoe.mccourse.block.entity;

import com.mojang.datafixers.types.Type;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.custom.CoalGeneratorBlockEntity;
import net.kaupenjoe.mccourse.block.entity.custom.CrystallizerBlockEntity;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {
    public static BlockEntityType<PedestalBlockEntity> PEDESTAL_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, MCCourseMod.id("pedestal_be"),
                    BlockEntityType.Builder.create(PedestalBlockEntity::new, ModBlocks.PEDESTAL).build(null));
    public static BlockEntityType<CrystallizerBlockEntity> CRYSTALLYZER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, MCCourseMod.id("crystallizer_be"),
                    BlockEntityType.Builder.create(CrystallizerBlockEntity::new, ModBlocks.CRYSTALLIZER).build(null));
    public static BlockEntityType<CoalGeneratorBlockEntity> COAL_GENERATOR_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, MCCourseMod.id("coal_generator_be"),
                    BlockEntityType.Builder.create(CoalGeneratorBlockEntity::new, ModBlocks.COAL_GENERATOR).build(null));

    public static void registerBlockEntities(){
        MCCourseMod.LOGGER.info("Registering Block Entities for " + MCCourseMod.MOD_ID);

        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.energyStorage, COAL_GENERATOR_BE);
        // Es necesario para que aparatos como cables se conecten a generadores
    }
}
