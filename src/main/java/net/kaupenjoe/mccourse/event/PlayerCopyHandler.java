package net.kaupenjoe.mccourse.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCopyHandler implements ServerPlayerEvents.CopyFrom{
    @Override // Se llama cuando hace respawn de un jugador, permitiendo copiar datos del jugador antiguo al nuevo
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean b) {
        /*((IEntityDataSaver) newPlayer).getPersistentData().putIntArray("mccourse.homepos",
                ((IEntityDataSaver) oldPlayer).getPersistentData().getIntArray("mccourse.homepos").get());*/ // 1.21.5<

        // Don't do this, otherwise it will give a AttachmentSyncException and kick you from the game // (Now this class became kind of obsolete)
        /*newPlayer.setAttached(ModAttachmentTypes.HOMEPOS_ATTACHMENT_TYPE,
                oldPlayer.getAttachedOrElse(ModAttachmentTypes.HOMEPOS_ATTACHMENT_TYPE, ModHomeposAttachedData.DEFAULT));*/
    }
}
