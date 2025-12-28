package net.kaupenjoe.mccourse.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.networking.packet.UpdatePedestalBlockPayload;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public class ModClientboundPackets {
    // We are on the client
    public static void handleUpdatePedestalBlockPayload(UpdatePedestalBlockPayload payload, ClientPlayNetworking.Context context) {
        ClientWorld world = context.client().world;

        if (world == null) {
            return;
        }
        BlockPos pos = payload.blockpos();

        // Clearing the block entity inventory on the client side
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
            pedestalBlockEntity.clear();
        }
    }
}
