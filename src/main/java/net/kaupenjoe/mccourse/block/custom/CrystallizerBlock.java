package net.kaupenjoe.mccourse.block.custom;

import com.mojang.serialization.MapCodec;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.custom.CrystallizerBlockEntity;
import net.kaupenjoe.mccourse.block.entity.custom.PedestalBlockEntity;
import net.kaupenjoe.mccourse.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystallizerBlock extends /*HorizontalFacingBlock*/BlockWithEntity {
    public static final MapCodec<CrystallizerBlock> CODEC = createCodec(CrystallizerBlock::new);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public CrystallizerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    /* FACING */

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        //return super.rotate(state, rotation);
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        //return super.mirror(state, mirror);
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    /* BLOCK ENTITY */

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        // CLIENT-ONLY
        double xPos = pos.getX() + 0.5f;
        double yPos = pos.getY() + 1.25f;
        double zPos = pos.getZ() + 0.5f;

        double offset = random.nextDouble() * 0.6 - 0.3;

        world.addParticle(ParticleTypes.SMOKE, xPos + offset, yPos, zPos + offset, 0.0f, 0.0f, 0.0f);
        world.addParticle(ParticleTypes.FLAME, xPos + offset, yPos, zPos + offset, 0.0f, 0.0f, 0.0f);
        world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModBlocks.FLUORITE_BLOCK.getDefaultState()),
                xPos + offset, yPos, zPos + offset, 0.0f, 0.0f, 0.0f);
        world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, Items.COOKED_BEEF.getDefaultStack()),
                xPos + offset, yPos, zPos + offset, 0.0f, 0.0f, 0.0f);
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrystallizerBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        //if (!state.isOf(newState.getBlock())) { // Esto también sirve
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrystallizerBlockEntity crystallizerBlockEntity) {
                //Inventory inventory = (Inventory)blockEntity;
                ItemScatterer.spawn(world, pos, crystallizerBlockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
        // Mirar clase ItemScatterer
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = ((CrystallizerBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ItemActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        //return super.getTicker(world, state, type);
        return validateTicker(type, ModBlockEntities.CRYSTALLYZER_BE, (world1, pos, state1, blockEntity) -> {
            blockEntity.tick(world1, pos, state1);
        });
    }
}
