package net.kaupenjoe.mccourse.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.kaupenjoe.mccourse.util.IEntityDataSaver;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerCopyHandler implements ServerPlayerEvents.CopyFrom{
    @Override // Se llama cuando hace respawn de un jugador, permitiendo copiar datos del jugador antiguo al nuevo
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean b) {
        ((IEntityDataSaver) newPlayer).getPersistentData().putIntArray("mccourse.homepos",
                ((IEntityDataSaver) oldPlayer).getPersistentData().getIntArray("mccourse.homepos").get());
    }
}
