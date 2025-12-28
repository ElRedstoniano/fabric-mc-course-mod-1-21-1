package net.kaupenjoe.mccourse.block.custom;


import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.networking.packet.UpdatePedestalBlockPayload;
import net.kaupenjoe.mccourse.util.TickableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 13, 14);
    public static final MapCodec<PedestalBlock> CODEC = PedestalBlock.createCodec(PedestalBlock::new);

    public PedestalBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL; // Si no se pone esto el bloque aparecerá invisible // Ya viene por defecto en la 1.21.5
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
       // return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        if(world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestalBlockEntity){
            if(pedestalBlockEntity.isEmpty() && !stack.isEmpty()){
                //pedestalBlockEntity.setStack(0, stack);
                //world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 2f);
                //stack.decrement(1);
                pedestalBlockEntity.setStack(0, stack);
                if (!world.isClient()) { //

                    stack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP,
                            SoundCategory.BLOCKS, 1f, 2f);
                }

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);
            } else if (stack.isEmpty() && !player.isSneaking()){
                ItemStack stackOnPedestal = pedestalBlockEntity.getStack(0);
                player.setStackInHand(Hand.MAIN_HAND, stackOnPedestal);
                world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                pedestalBlockEntity.clear(); // Since there is only 1 slot it clears all the inventory
                // For some reason this doesn't work at all on the client side so a custom payload is needed

                pedestalBlockEntity.markDirty();
                world.updateListeners(pos, state, state, 0);

                if (!world.isClient()) {
                    UpdatePedestalBlockPayload payload = new UpdatePedestalBlockPayload(pos); // Custom payload sending the blockpos
                    for (ServerPlayerEntity playerFromServer : PlayerLookup.world((ServerWorld) world)) {
                        //playerFromServer.networkHandler.sendPacket(pedestalBlockEntity.toUpdatePacket());
                        ServerPlayNetworking.send(playerFromServer, payload);
                    }
                }
            } else if (player.isSneaking() && !world.isClient()){
                player.openHandledScreen(pedestalBlockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        //return TickableBlockEntity.getTicker(world);
        return world.isClient() ? validateTicker(type, ModBlockEntities.PEDESTAL_BE, TickableBlockEntity.getTicker(world)) : null;
        // Este también sirve ^, es otra manera
    }
}
