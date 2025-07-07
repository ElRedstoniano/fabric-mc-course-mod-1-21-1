package net.kaupenjoe.mccourse.networking;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record UpdatePedestalBlockPayload(BlockPos blockpos) implements CustomPayload {

    public static final Identifier UPDATE_PEDESTAL_PAYLOAD_ID = MCCourseMod.id("update_pedestal");
    public static final CustomPayload.Id<UpdatePedestalBlockPayload> ID = new CustomPayload.Id<>(UPDATE_PEDESTAL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, UpdatePedestalBlockPayload> CODEC =
            PacketCodec.tuple(BlockPos.PACKET_CODEC, UpdatePedestalBlockPayload::blockpos, UpdatePedestalBlockPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
