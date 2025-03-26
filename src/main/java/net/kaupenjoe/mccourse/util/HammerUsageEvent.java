package net.kaupenjoe.mccourse.util;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.kaupenjoe.mccourse.item.custom.HammerItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class HammerUsageEvent implements PlayerBlockBreakEvents.Before{
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/c23d117dcd3b3b3408a138716b15507f709494cd/src/main/java/cofh/core/event/AreaEffectEvents.java
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    // El método_ se llama cada vez que se rompe un bloque
    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity playerEntity, BlockPos pos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        ItemStack mainHandItem = playerEntity.getMainHandStack();

        if(mainHandItem.getItem() instanceof HammerItem hammer && playerEntity instanceof ServerPlayerEntity serverPlayer) {
            if(HARVESTED_BLOCKS.contains(pos)) {
                return true; // Broken block
            }

            for (BlockPos position : HammerItem.getBlocksToBeDestroyed(1, pos, serverPlayer)) {
                if (pos == position || !hammer.isCorrectForDrops(mainHandItem, world.getBlockState(position))) {
                    // Si la posición actual es la principal que se está destruyendo actualmente o si la posición que se está intentando
                    // destruir no es la correcta
                    continue;
                }

                // Es necesario tener una lista temporal de bloques a destruir, ya que al destruir otro bloque el mismo evento
                // se vuelve a llamar y se volvería a empezar desde el principio ejecutando el mismo método_ cambiando la
                // posición inicial, y al comprobar si el bloque destruido ya estaba añadido a la lista se puede evitar
                // volver a ejecutar el mismo evento para otros bloques que no sean el de la posición inicial.
                HARVESTED_BLOCKS.add(position);
                serverPlayer.interactionManager.tryBreakBlock(position); // vuelve a llamar a beforeBlockBreak
                HARVESTED_BLOCKS.remove(position);
            }
        }

        return true; // Block not broken
    }
}
