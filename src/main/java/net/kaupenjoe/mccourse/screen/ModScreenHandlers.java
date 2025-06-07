package net.kaupenjoe.mccourse.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.screen.custom.CrystallizerScreenHandler;
import net.kaupenjoe.mccourse.screen.custom.PedestalScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                MCCourseMod.id("pedestal_screen_handler"),
                new ExtendedScreenHandlerType<>(PedestalScreenHandler::new, BlockPos.PACKET_CODEC));
    public static final ScreenHandlerType<CrystallizerScreenHandler> CRYSTALLIZER_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                MCCourseMod.id("crystallizer_screen_handler"),
                new ExtendedScreenHandlerType<>(CrystallizerScreenHandler::new, BlockPos.PACKET_CODEC));

    /*public static final ScreenHandlerType<PedestalScreenHandler> PEDESTAL_SCREEN_HANDLER = register("pedestal_screen_handler",
                new ExtendedScreenHandlerType<>(PedestalScreenHandler::new, BlockPos.PACKET_CODEC));*/
    // Pasa un packetCodec que funciona como una información extra que se pasa al PedestalScreenHandler
    // ExtendedScreenHandlerType es una clase que obtiene un ScreenHandler que tiene un poco de funcionalidad extendida respecto a su clase base


    public static void registerScreenHandlers(){
        MCCourseMod.LOGGER.info("Registering screen handlers for " + MCCourseMod.MOD_ID);
    }

    private static <T extends ScreenHandler, D> ScreenHandlerType<T> register(String id,  ExtendedScreenHandlerType<T, D> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, MCCourseMod.id(id),
                factory);
    }
}
