package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kaupenjoe.mccourse.util.IEntityDataSaver;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SetHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment){
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("home")
                        //.requires(source -> source.hasPermissionLevel(2)) // Si tiene permisos de administrador
                .then(CommandManager.literal("set").executes(SetHomeCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        IEntityDataSaver player = ((IEntityDataSaver) context.getSource().getPlayer());
        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
        String positionString = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";

        player.getPersistentData().putIntArray("mccourse.homepos",
                new int[]{playerPos.getX(), playerPos.getY(), playerPos.getZ()});
        context.getSource().sendFeedback(() -> Text.translatable("mccourse.command.set_homepos", positionString), true);
        return 1;
    }
}
