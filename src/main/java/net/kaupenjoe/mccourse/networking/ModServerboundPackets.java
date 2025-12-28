package net.kaupenjoe.mccourse.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.networking.packet.KaupenPayload;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;

public class ModServerboundPackets {
    // We are on the server
    public static void handleKaupenPayload(KaupenPayload kaupenPayload, ServerPlayNetworking.Context context) {
        //EntityType.COW.spawn(context.player().getEntityWorld(), context.player().getBlockPos(), SpawnReason.TRIGGERED);
        //context.player().sendMessage(Text.literal(kaupenPayload.name() + " with a value of " + kaupenPayload.value()));
        context.player().openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, playerInventory, playerEntity) ->
                        GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, context.player().getEnderChestInventory()),
                Text.translatable("mccourse.container.kaupender_chest")
        ));
    }
}
