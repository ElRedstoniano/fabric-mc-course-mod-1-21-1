package net.kaupenjoe.mccourse.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kaupenjoe.mccourse.block.entity.attachments.ModHomeposAttachedData;
import net.kaupenjoe.mccourse.block.entity.attachments.types.ModAttachmentTypes;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

// Saving data to entities is no longer with nbt components.
public class SetHomeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess,
                                CommandManager.RegistrationEnvironment registrationEnvironment){
        serverCommandSourceCommandDispatcher.register(CommandManager.literal("home")
                        //.requires(source -> source.hasPermissionLevel(2)) // Si tiene permisos de administrador
                .then(CommandManager.literal("set").executes(SetHomeCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) {
        /*IEntityDataSaver player = ((IEntityDataSaver) context.getSource().getPlayer());
        BlockPos playerPos = context.getSource().getPlayer().getBlockPos();
        String positionString = "(" + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ() + ")";*/
        /*player.getPersistentData().putIntArray("mccourse.homepos",
                new int[]{playerPos.getX(), playerPos.getY(), playerPos.getZ()});*/
        //context.getSource().getPlayer().writeData();
        /*player.getPersistentData(). putIntArray("mccourse.homepos",
                new int[]{playerPos.getX(), playerPos.getY(), playerPos.getZ()});
        context.getSource().sendFeedback(() -> Text.translatable("mccourse.command.set_homepos", positionString), true);*/ // 1.21.5<

        //ServerWorld serverWorld = context.getSource().getWorld();
        ServerPlayerEntity player = context.getSource().getPlayer();
        Vec3d actualPos = context.getSource().getPlayer().getPos();
        ModHomeposAttachedData data = player.getAttachedOrElse(ModAttachmentTypes.HOMEPOS_ATTACHMENT_TYPE, ModHomeposAttachedData.DEFAULT);
        player.setAttached(ModAttachmentTypes.HOMEPOS_ATTACHMENT_TYPE, data.setHomePos(actualPos));

        String positionString = "(" + actualPos.getX() + ", " + actualPos.getY() + ", " + actualPos.getZ() + ")";
        context.getSource().sendFeedback(() -> Text.translatable("mccourse.command.set_homepos", positionString), true);
        return 1;
    }
}
