package net.kaupenjoe.mccourse.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.networking.packet.KaupenPayload;
import net.kaupenjoe.mccourse.networking.packet.UpdatePedestalBlockPayload;
import net.minecraft.network.RegistryByteBuf;

public class ModPayloadsRegisterer {
    private static void registerClientbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // This payload is sent to the client
        registry.register(UpdatePedestalBlockPayload.ID, UpdatePedestalBlockPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(UpdatePedestalBlockPayload.ID, ModClientboundPackets::handleUpdatePedestalBlockPayload);
        // ^^ This was previously indicated on the MCCourseModClient class but for refactoring it also can be declared here
    }
    private static void registerServerbound(PayloadTypeRegistry<RegistryByteBuf> registry) {
        // This payload is sent to the server
        registry.register(KaupenPayload.ID, KaupenPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(KaupenPayload.ID, ModServerboundPackets::handleKaupenPayload);
    }

    public static void registerPayloads() {
        registerServerbound(PayloadTypeRegistry.playC2S());
        registerClientbound(PayloadTypeRegistry.playS2C());
        //PayloadTypeRegistry.playS2C().register(UpdatePedestalBlockPayload.ID, UpdatePedestalBlockPayload.CODEC);
    }
}
