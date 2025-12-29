package net.kaupenjoe.mccourse.mana;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.data.attachments.types.ModAttachmentTypes;
import net.kaupenjoe.mccourse.networking.packet.ManaPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class ManaHandler {
    public static void setMana(ServerPlayerEntity player, int value) {
        Integer oldManaValue = player.getAttached(ModAttachmentTypes.MANA);
        oldManaValue = oldManaValue == null ? 0 : oldManaValue;
        player.setAttached(ModAttachmentTypes.MANA, value);
        ServerPlayNetworking.send(player, new ManaPayload(oldManaValue, value)); // Updating HUD when joining / initial client syncronization
    }

    public static void addMana(ServerPlayerEntity player, int value) {
        Integer oldManaValue = player.getAttached(ModAttachmentTypes.MANA);
        oldManaValue = oldManaValue == null ? 0 : oldManaValue;
        int newManaValue = oldManaValue + value;
        player.setAttached(ModAttachmentTypes.MANA, newManaValue);
        ServerPlayNetworking.send(player, new ManaPayload(oldManaValue, newManaValue)); // Updating HUD when joining / initial client syncronization
    }
    public static void removeMana(ServerPlayerEntity player, int value) {
        Integer oldManaValue = player.getAttached(ModAttachmentTypes.MANA);
        oldManaValue = oldManaValue == null ? 0 : oldManaValue;
        int newManaValue = oldManaValue - value;
        player.setAttached(ModAttachmentTypes.MANA, newManaValue);
        ServerPlayNetworking.send(player, new ManaPayload(oldManaValue, newManaValue)); // Updating HUD when joining / initial client syncronization
    }
}
