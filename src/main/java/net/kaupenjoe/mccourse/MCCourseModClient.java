package net.kaupenjoe.mccourse;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.renderer.PedestalBlockEntityRenderer;
import net.kaupenjoe.mccourse.block.entity.renderer.TankBlockEntityRenderer;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.client.*;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.screen.ModScreenHandlers;
import net.kaupenjoe.mccourse.screen.custom.*;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.world.biome.GrassColors;

public class MCCourseModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLUORITE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLUORITE_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACKWOOD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TANK, RenderLayer.getTranslucent());

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : GrassColors.getDefaultColor(), ModBlocks.COLORED_LEAVES);
        //ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getDefaultColor(), ModBlocks.COLORED_LEAVES);
        // Mirar clases BlockColors e ItemColor para más ejemplos

        //ModModelPredicates.registerModelPredicates(); // Gone in 1.12.4

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_FLUORITE_WATER, ModFluids.FLOWING_FLUORITE_WATER,
                SimpleFluidRenderHandler.coloredWater(0xA1E038D0));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_FLUORITE_WATER, ModFluids.FLOWING_FLUORITE_WATER);
        // https://wiki.fabricmc.net/tutorial:fluids para más ejemplos

        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.TANK_BE, TankBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.CRYSTALLIZER_SCREEN_HANDLER, CrystallyzerScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.TANK_SCREEN_HANDLER, TankScreen::new);
        HandledScreens.register(ModScreenHandlers.WARTURTLE_SCREEN_HANDLER, WarturtleScreen::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.DODO, DodoModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.DODO_ET, DodoRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.DODO_BABY, DodoModel::getTexturedBabyModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.GIRAFFE, GiraffeModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.GIRAFFE_ET, GiraffeRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.GIRAFFE_BABY, GiraffeModel::getTexturedBabyModelData);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.TOMAHAWK, TomahawkProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TOMAHAWK_ET, TomahawkProjectileRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE, WarturtleModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE_BABY,
                WarturtleModel::getTexturedBabyModelData);
        EntityRendererRegistry.register(ModEntities.WARTURTLE_ET, WarturtleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE_ARMOR, WarturtleModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ModEntityModelLayers.WARTURTLE_BABY_ARMOR, WarturtleModel::getTexturedBabyModelData);
        //
    }
}
