package net.kaupenjoe.mccourse.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.kaupenjoe.mccourse.data.attachments.types.ModAttachmentTypes;

public class ModServerEvents {
    public static void runServerEvents() {
        ServerPlayerEvents.COPY_FROM.register(new PlayerCopyHandler()); // (Obsolete right now)

        ServerPlayerEvents.JOIN.register(player -> {
            player.setAttached(ModAttachmentTypes.MANA, 5);
        }); // Only server logic / no client syncronization
    }
}
