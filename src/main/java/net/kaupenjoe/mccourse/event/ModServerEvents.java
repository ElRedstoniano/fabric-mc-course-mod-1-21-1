package net.kaupenjoe.mccourse.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.data.attachments.types.ModAttachmentTypes;
import net.kaupenjoe.mccourse.mana.ManaHandler;
import net.kaupenjoe.mccourse.networking.packet.ManaPayload;

public class ModServerEvents {
    public static void runServerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler()); // (Obsolete right now)

        ServerPlayerEvents.JOIN.register(player -> { // Only server logic / no client syncronization
            //player.setAttached(ModAttachmentTypes.MANA, 5);
            //ServerPlayNetworking.send(player, new ManaPayload(0, 5)); // Updating HUD when joining / initial client syncronization
            ManaHandler.setMana(player, 5); // Syncronization done by the handler
        });

        // When dying, the mana is passed and synchronized to the new plater
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            ManaHandler.setMana(newPlayer, oldPlayer.getAttached(ModAttachmentTypes.MANA));
        });

        // The same thing but in another way (COPY_FROM seems to not work sometimes for some reason)
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            ManaHandler.setMana(newPlayer, oldPlayer.getAttached(ModAttachmentTypes.MANA));
        });
    }
}
