package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.kaupenjoe.mccourse.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class ReturnHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment){
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("home")
                .then(CommandManager.literal("return").executes(ReturnHomeCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        IEntityDataSaver player = (IEntityDataSaver) context.getSource().getPlayer();

        int[] homepos = player.getPersistentData().getIntArray("mccourse.homepos");

        if (homepos.length != 0){
            context.getSource().getPlayer().requestTeleport(homepos[0], homepos[1], homepos[2]);
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.player_returned_home"), false);
            return 1;
        } else {
            context.getSource().sendFeedback(() -> Text.translatable("mccourse.no_home_pos"), false);
            return -1;
        }
    }

}
