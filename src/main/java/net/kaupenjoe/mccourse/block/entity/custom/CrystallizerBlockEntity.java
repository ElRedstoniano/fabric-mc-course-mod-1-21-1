package net.kaupenjoe.mccourse.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.custom.CrystallizerBlock;
import net.kaupenjoe.mccourse.block.entity.ImplementedInventory;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.item.ModItems;
import net.kaupenjoe.mccourse.recipe.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.CrystallizerRecipeInput;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.kaupenjoe.mccourse.screen.custom.CrystallizerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CrystallizerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int FLUID_ITEM_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private static final int ENERGY_ITEM_SLOT = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private final int DEFAULT_MAX_PROGRESS = 72;

    public CrystallizerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CRYSTALLYZER_BE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> CrystallizerBlockEntity.this.progress;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> CrystallizerBlockEntity.this.progress = value;
                    case 1 -> CrystallizerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        Direction localDir = this.getWorld().getBlockState(pos).get(CrystallizerBlock.FACING);

        if(side == null) {
            return false;
        }

        if (side == Direction.DOWN) {
            return false;
        }

        if (side == Direction.UP) {
            return slot == INPUT_SLOT;
        }

        return switch (localDir) {
            default -> // NORTH
                side == Direction.NORTH && INPUT_SLOT == slot ||
                        side == Direction.EAST && INPUT_SLOT == slot ||
                        side == Direction.WEST && INPUT_SLOT == slot;
            case EAST ->
            side.rotateYCounterclockwise() == Direction.NORTH && INPUT_SLOT == slot ||
                    side.rotateYCounterclockwise() == Direction.EAST && INPUT_SLOT == slot ||
                    side.rotateYCounterclockwise() == Direction.WEST && INPUT_SLOT == slot;
            case SOUTH ->
            side.getOpposite() == Direction.NORTH && INPUT_SLOT == slot ||
                    side.getOpposite() == Direction.EAST && INPUT_SLOT == slot ||
                    side.getOpposite() == Direction.WEST && INPUT_SLOT == slot;
            case WEST ->
            side.rotateYClockwise() == Direction.NORTH && INPUT_SLOT == slot ||
                    side.rotateYClockwise() == Direction.EAST && INPUT_SLOT == slot ||
                    side.rotateYClockwise() == Direction.WEST && INPUT_SLOT == slot;
        };
        //return ImplementedInventory.super.canInsert(slot, stack, side);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(CrystallizerBlock.FACING);

        if (side == Direction.UP) {
            return false;
        }

        // Down extract
        if (side == Direction.DOWN) {
            return slot == OUTPUT_SLOT;
        }

        // bottom extract
        // right extract
        return switch (localDir){
            default -> side == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side == Direction.EAST && slot == OUTPUT_SLOT;
            case EAST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYCounterclockwise() == Direction.EAST && slot == OUTPUT_SLOT;
            case SOUTH -> side.getOpposite() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.getOpposite() == Direction.EAST && slot == OUTPUT_SLOT;
            case WEST -> side.rotateYClockwise() == Direction.SOUTH && slot == OUTPUT_SLOT ||
                    side.rotateYClockwise() == Direction.EAST && slot == OUTPUT_SLOT;

        };
        //return ImplementedInventory.super.canExtract(slot, stack, side);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.mccourse.crystallizer");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CrystallizerScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("crystallizer.progress", progress);
        nbt.putInt("crystallizer.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        super.readNbt(nbt, registryLookup);
        progress = nbt.getInt("crystallizer.progress");
        maxProgress = nbt.getInt("crystallizer.max_progress");
    }

    public void tick(World world, BlockPos blockPos, BlockState state){
        if (world.isClient()){
            return;
        }

        // Server side

        if(hasRecipe() && canInsertIntoOutputSlot()) {
            increaseCraftingProgress();

            /*if(!world.getBlockState(pos).get(CrystallizerBlock.LIT)){
                world.setBlockState(pos, state.with(CrystallizerBlock.LIT, true));
            }*/
            //if (!world.isClient()) {
                world.setBlockState(pos, state.with(CrystallizerBlock.LIT, true));
            //}
            markDirty(world, blockPos, state);

            if (hasCraftingFinished()){
                craftItem();
                resetProgress();
            }
        } else {
            //if (!world.isClient()) {
                world.setBlockState(pos, state.with(CrystallizerBlock.LIT, false));
            //}
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        Optional<RecipeEntry<CrystallizerRecipe>> recipe = getCurrentRecipe();

        this.removeStack(INPUT_SLOT, 1);
        //this.setStack(OUTPUT_SLOT, new ItemStack(ModItems.FLUORITE,  this.getStack(OUTPUT_SLOT).getCount() + 1));
        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().output().getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + recipe.get().value().output().getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        this.progress++;
    }

    // Si se puede añadir al menos un item
    private boolean canInsertIntoOutputSlot() {
        return this.getStack(OUTPUT_SLOT).isEmpty() ||
                this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean hasRecipe() {
        /*ItemStack input = new ItemStack(ModItems.RAW_FLUORITE);
        ItemStack output = new ItemStack(ModItems.FLUORITE);

        return this.getStack(INPUT_SLOT).getItem() == input.getItem() &&
               canInsertAmountIntoOutputSlot(output.getCount()) &&  canInsertItemIntoOutputSlot(output);*/
        Optional<RecipeEntry<CrystallizerRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()){
            return false;
        }
        //ItemStack output = recipe.get().value().output();
        ItemStack output = recipe.get().value().getResult(null); // También sirve
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeEntry<CrystallizerRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager().getFirstMatch(ModRecipes.CRYSTALLIZER_TYPE, new CrystallizerRecipeInput(inventory.get(INPUT_SLOT)), this.getWorld());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        //return this.getStack(OUTPUT_SLOT).getMaxCount() >= this.getStack(OUTPUT_SLOT).getCount() + count;
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount(); // El aire lo cuenta como 1
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
