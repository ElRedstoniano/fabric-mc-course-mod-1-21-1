package net.kaupenjoe.mccourse.networking.packet;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record KaupenPayload(String name, int value) implements CustomPayload {
    public static final CustomPayload.Id<KaupenPayload> ID = new CustomPayload.Id<>(MCCourseMod.id("kaupen_payload"));
    public static final PacketCodec<RegistryByteBuf, KaupenPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            KaupenPayload::name,
            PacketCodecs.VAR_INT,
            KaupenPayload::value,
            KaupenPayload::new
    );
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
