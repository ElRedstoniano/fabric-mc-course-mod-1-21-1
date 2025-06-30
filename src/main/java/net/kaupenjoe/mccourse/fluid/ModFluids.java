package net.kaupenjoe.mccourse.fluid;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ModFluids {
    public static final FlowableFluid STILL_FLUORITE_WATER = Registry.register(Registries.FLUID,
            MCCourseMod.id("fluorite_water"), new FluoriteWaterFluid.Still());
    public static final FlowableFluid FLOWING_FLUORITE_WATER = Registry.register(Registries.FLUID,
            MCCourseMod.id("flowing_fluorite_water"), new FluoriteWaterFluid.Flowing());

    public static final Block FLUORITE_WATER_BLOCK = Registry.register(Registries.BLOCK,
            MCCourseMod.id("fluorite_water_block"), new FluidBlock(ModFluids.STILL_FLUORITE_WATER,
                    AbstractBlock.Settings.copy(Blocks.WATER).replaceable().liquid()
                            .registryKey(RegistryKey.of(RegistryKeys.BLOCK, MCCourseMod.id("fluorite_water_block")))));

    public static final Item FLUORITE_WATER_BUCKET = Registry.register(Registries.ITEM, MCCourseMod.id("fluorite_water_bucket"),
            new BucketItem(ModFluids.STILL_FLUORITE_WATER,
            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)
                    .registryKey(RegistryKey.of(RegistryKeys.ITEM, MCCourseMod.id("fluorite_water_bucket")))));

    public static void registerFluids(){
        MCCourseMod.LOGGER.info("Registering fluids for " + MCCourseMod.MOD_ID);
    }

}
