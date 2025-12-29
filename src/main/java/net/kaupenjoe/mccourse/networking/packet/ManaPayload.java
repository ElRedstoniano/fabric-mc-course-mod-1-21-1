package net.kaupenjoe.mccourse.networking.packet;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ManaPayload(int oldValue, int newValue) implements CustomPayload {
    public static final CustomPayload.Id<ManaPayload> ID = new CustomPayload.Id<>(MCCourseMod.id("mana_payload"));
    public static final PacketCodec<RegistryByteBuf, ManaPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            ManaPayload::oldValue,
            PacketCodecs.VAR_INT,
            ManaPayload::newValue,
            ManaPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
