package net.kaupenjoe.mccourse.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModPayloadsRegisterer {
    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(UpdatePedestalBlockPayload.ID, UpdatePedestalBlockPayload.CODEC);
    }
}
