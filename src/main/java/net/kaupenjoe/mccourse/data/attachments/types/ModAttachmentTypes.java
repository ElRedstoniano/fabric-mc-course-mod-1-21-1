package net.kaupenjoe.mccourse.data.attachments.types;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.data.attachments.ModHomeposAttachedData;

// From: https://gist.github.com/Linguardium/cebcd41c6bbcd74eaa1f8b40ec2bbec8
public class ModAttachmentTypes {
  // Register a type of attached data. This data can be attached to anything, this is only a type
  public static final AttachmentType<ModHomeposAttachedData> HOMEPOS_ATTACHMENT_TYPE = AttachmentRegistry.create(
          MCCourseMod.id("homepos"),
          builder-> builder // we are using a builder chain here to configure the attachment data type
                  .initializer(()-> ModHomeposAttachedData.DEFAULT) // a default value to provide if you dont supply one
            .persistent(ModHomeposAttachedData.CODEC) // how to save and load the data when the object it is attached to is saved or loaded
            .syncWith(
          ModHomeposAttachedData.PACKET_CODEC,  // how to turn the data into a packet to send to players
              AttachmentSyncPredicate.all() // who to send the data to
            ).copyOnDeath()
 );

  public static final AttachmentType<Integer> MANA = AttachmentRegistry.createPersistent(
          MCCourseMod.id("mana"), Codec.INT
  );

  public static void registerModData() {
    // This empty method can be called from the mod initializer to ensure our component type is registered at mod initialization time
      MCCourseMod.LOGGER.info("Registering ModData for " + MCCourseMod.MOD_ID);
  }
}