package net.kaupenjoe.mccourse;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.block.entity.renderer.PedestalBlockEntityRenderer;
import net.kaupenjoe.mccourse.block.entity.renderer.TankBlockEntityRenderer;
import net.kaupenjoe.mccourse.data.attachments.types.ModAttachmentTypes;
import net.kaupenjoe.mccourse.entity.ModEntities;
import net.kaupenjoe.mccourse.entity.client.*;
import net.kaupenjoe.mccourse.fluid.ModFluids;
import net.kaupenjoe.mccourse.keybind.ModKeyBinds;
import net.kaupenjoe.mccourse.networking.ModServerboundPackets;
import net.kaupenjoe.mccourse.networking.packet.KaupenPayload;
import net.kaupenjoe.mccourse.networking.packet.UpdatePedestalBlockPayload;
import net.kaupenjoe.mccourse.screen.ModScreenHandlers;
import net.kaupenjoe.mccourse.screen.custom.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.GrassColors;

public class MCCourseModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /* Block layers */
        BlockRenderLayerMap.putBlock(ModBlocks.FLUORITE_DOOR, /*RenderLayer.getCutout() 1.21.5<*/ BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.FLUORITE_TRAPDOOR, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.STRAWBERRY_CROP, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.DAHLIA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.POTTED_DAHLIA, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.BLACKWOOD_SAPLING, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(ModBlocks.TANK, /*RenderLayer.getTranslucent() 1.21.5*/ BlockRenderLayer.TRANSLUCENT);

        /* Block colors */

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : GrassColors.getDefaultColor(), ModBlocks.COLORED_LEAVES);
        //ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColors.getDefaultColor(), ModBlocks.COLORED_LEAVES);
        // Mirar clases BlockColors e ItemColor for more examples

        //ModModelPredicates.registerModelPredicates(); // Gone in 1.12.4

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_FLUORITE_WATER, ModFluids.FLOWING_FLUORITE_WATER,
                SimpleFluidRenderHandler.coloredWater(0xA1E038D0));

        /* Fluids */

        //BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.STILL_FLUORITE_WATER, ModFluids.FLOWING_FLUORITE_WATER); // 1.21.5
        BlockRenderLayerMap.putFluids(BlockRenderLayer.TRANSLUCENT, ModFluids.STILL_FLUORITE_WATER, ModFluids.FLOWING_FLUORITE_WATER);
        // https://wiki.fabricmc.net/tutorial:fluids for more examples

        /* Block entity Renderers */
        BlockEntityRendererFactories.register(ModBlockEntities.PEDESTAL_BE, PedestalBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.TANK_BE, TankBlockEntityRenderer::new);

        /* Block entity Screens */
        HandledScreens.register(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, PedestalScreen::new);
        HandledScreens.register(ModScreenHandlers.CRYSTALLIZER_SCREEN_HANDLER, CrystallyzerScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.TANK_SCREEN_HANDLER, TankScreen::new);
        HandledScreens.register(ModScreenHandlers.WARTURTLE_SCREEN_HANDLER, WarturtleScreen::new);

        /* Entity model layers */
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

        // KeyBinds
        ModKeyBinds.registerKeys();
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (ModKeyBinds.K_KEYBIND.wasPressed()) {
                minecraftClient.player.sendMessage(Text.literal("I just pressed the K Key! - " + minecraftClient.player.getStringifiedName()), false);
                ClientPlayNetworking.send(new KaupenPayload("Kaupenjoe", 67));
            }
        });

        /* Networking - registering client payload reciever */
        //ClientPlayNetworking.registerGlobalReceiver(UpdatePedestalBlockPayload.ID, ModServerboundPackets::handleUpdatePedestalBlockPayload);
        // Actually done in ModPayloadsRegisterer class


        // Rendering Mana Icons
        // This was done with HudRenderCallback until 1.21.3
        // https://docs.fabricmc.net/1.21.10/develop/rendering/hud
        HudElementRegistry.attachElementBefore(VanillaHudElements.ARMOR_BAR, MCCourseMod.id("mana_display"), MCCourseModClient::render);
    }

    private static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        int x = drawContext.getScaledWindowWidth() / 2; // X centered
        int y = drawContext.getScaledWindowHeight();

        if (!MinecraftClient.getInstance().player.isInCreativeMode()
                && MinecraftClient.getInstance().player.hasAttached(ModAttachmentTypes.MANA)) {
            // Mana background icons
            for (int i = 0; i < 5; i++) {
                drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, MCCourseMod.id("mana_icon_bg"),
                        16, 16, 0, 0, x - 95 + i * 18, y - 55, 16, 16);
            }
            // Actual mana capacity
            for (int i = 0; i < MinecraftClient.getInstance().player.getAttached(ModAttachmentTypes.MANA); i++) {
                drawContext.drawGuiTexture(RenderPipelines.GUI_TEXTURED, MCCourseMod.id("mana_icon"),
                        16, 16, 0, 0, x - 95 + i * 18, y - 55, 16, 16);
            }
        }
    }
}
