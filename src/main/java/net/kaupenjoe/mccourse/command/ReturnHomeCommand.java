package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kaupenjoe.mccourse.data.attachments.ModHomeposAttachedData;
import net.kaupenjoe.mccourse.data.attachments.types.ModAttachmentTypes;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class ReturnHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment){
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("return").executes(ReturnHomeCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        /*IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();

        //int[] homepos = player.getPersistentData().getIntArray("mccourse.homepos").get();
        Optional<Vec3d> homepos = player.getPersistentData(); // Not from 1.21.5 but first I tried without success to modify it (just ignore it)

        //if (homepos.length != 0){
        if (homepos.get() != null){
            context.getSource().getPlayer().requestTeleport(homepos.get().getX(), homepos.get().getY(), homepos.get().getZ());
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.player_returned_home"), false);
            return 1;
        } else {
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.no_home_pos"), false);
            return -1;
        }*/ // 1.21.5<

        ServerPlayerEntity player = context.getSource().getPlayer();
        ModHomeposAttachedData data = player.getAttachedOrElse(ModAttachmentTypes.HOMEPOS_ATTACHMENT_TYPE, ModHomeposAttachedData.DEFAULT);

        Optional<Vec3d> homepos = data.homePos();

        if (homepos.isPresent()) {
            context.getSource().getPlayer().requestTeleport(homepos.get().getX(), homepos.get().getY(), homepos.get().getZ());
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.player_returned_home"), false);
            return 1;
        } else {
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.no_home_pos"), false);
            return -1;
        }
    }

}
