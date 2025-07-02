package net.kaupenjoe.mccourse.block.custom;

import com.mojang.serialization.MapCodec;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.block.entity.custom.CrystallizerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrystallizerBlock extends /*HorizontalFacingBlock*/BlockWithEntity {
    public static final MapCodec<CrystallizerBlock> CODEC = createCodec(CrystallizerBlock::new);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = Properties.LIT;

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
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(LIT, false);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    /* LIT */
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT)) {
            return;
        }
        // Mirar clase de ejemplo FurnaceBlock
        double xPos = (double)pos.getX() + 0.5;
        double yPos = pos.getY();
        double zPos = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.15) {
            //world.playSound(xPos, yPos, zPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
            world.playSound(null, xPos, yPos, zPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }

        Direction direction = state.get(FACING);
        Direction.Axis axis = direction.getAxis();

        double defaultOffset = random.nextDouble() * 0.6 - 0.3;
        double xOffsets = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : defaultOffset;
        double yOffset = random.nextDouble() * 6.0 / 8.0;
        double zOffset = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : defaultOffset;

        // addParticle -> addParticleClient
        world.addParticleClient(ParticleTypes.SMOKE, xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);

        if(world.getBlockEntity(pos) instanceof CrystallizerBlockEntity crystallizerBlockEntity && !crystallizerBlockEntity.getStack(1).isEmpty()) {
            world.addParticleClient(new ItemStackParticleEffect(ParticleTypes.ITEM, crystallizerBlockEntity.getStack(1)),
                    xPos + xOffsets, yPos + yOffset, zPos + zOffset, 0.0, 0.0, 0.0);
        }


        // CLIENT-ONLY // Partículas viejas
        double xPos_ = pos.getX() + 0.5f;
        double yPos_ = pos.getY() + 1.25f;
        double zPos_ = pos.getZ() + 0.5f;

        double offset = random.nextDouble() * 0.6 - 0.3;

        world.addParticleClient(ParticleTypes.SMOKE, xPos_ + offset, yPos_, zPos_ + offset, 0.0f, 0.0f, 0.0f);
        world.addParticleClient(ParticleTypes.FLAME, xPos_ + offset, yPos_, zPos_ + offset, 0.0f, 0.0f, 0.0f);
        world.addParticleClient(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModBlocks.FLUORITE_BLOCK.getDefaultState()),
                xPos_ + offset, yPos_, zPos_ + offset, 0.0f, 0.0f, 0.0f);
        world.addParticleClient(new ItemStackParticleEffect(ParticleTypes.ITEM, Items.COOKED_BEEF.getDefaultStack()),
                xPos_ + offset, yPos_, zPos_ + offset, 0.0f, 0.0f, 0.0f);
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

    @Override // onUseWithItems -> onUseWithItem
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient){
            NamedScreenHandlerFactory screenHandlerFactory = ((CrystallizerBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        //return super.getTicker(world, state, type);
        return validateTicker(type, ModBlockEntities.CRYSTALLYZER_BE, (world1, pos, state1, blockEntity) -> {
            blockEntity.tick(world1, pos, state1);
        });
    }
}
