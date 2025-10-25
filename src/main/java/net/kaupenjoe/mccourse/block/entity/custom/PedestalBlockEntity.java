package net.kaupenjoe.mccourse.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.screen.custom.PedestalScreenHandler;
import net.kaupenjoe.mccourse.util.TickableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PedestalBlockEntity extends BlockEntity implements Inventory, /*BlockEntityTicker<PedestalBlockEntity>,*/ TickableBlockEntity,
        ExtendedScreenHandlerFactory<BlockPos>, HeldItemContext {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public int ticks = 0;
    //private float rotation = 0;
    //private float levitationOffset = 0;
    private final float ROTATION_SPEED = 2F;
    private final float LEVITATION_SPEED = 0.05F;
    private final float LEVITATION_RANGE_DIVISOR = 8F;

    private Optional<Integer> itemColor;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL_BE, pos, state);
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < size(); i++){
            ItemStack stack = getStack(i);
            if (!stack.isEmpty()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        markDirty(); // Actualizar lo que pasa dentro del blockentity
        return slot >= this.size() ? ItemStack.EMPTY : this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        /*ItemStack stack = inventory.get(slot);
        stack.decrement(amount);
        return inventory.set(slot, stack);*/ // No va bien
        return Inventories.removeStack(inventory, slot); // Este si sirve
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        inventory.set(slot, stack.copyWithCount(1));
        //inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override // In 1.21.6 writeNbt -> writeData
    protected void writeData(WriteView writeView) {
        super.writeData(writeView);
        Inventories.writeData(writeView, inventory);
    }

    @Override // In 1.21.6 readNbt -> readData
    protected void readData(ReadView readView) {
        super.readData(readView);
        itemColor = readView.getOptionalInt("color");
        Inventories.readData(readView, inventory);
    }

    public Optional<Integer> getItemColor(){
        return itemColor;
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        ItemScatterer.spawn(world, pos, this); // Mirar clase ItemScatterer
        //world.updateComparators(pos, this);
        super.onBlockReplaced(pos, oldState);
    }

    public float getRenderingRotation(float tickDelta){
        /*rotation += (getWorld().getTime() + tickDelta) * ROTATION_SPEED;
        if (rotation >= 360){
            rotation = 0;
        }*/
        //return ((getWorld().getTime() + tickDelta) * ROTATION_SPEED) % 360; // También se podría hacer así
        return ((this.ticks + tickDelta) * ROTATION_SPEED) % 360;
    }

    public float getRenderingLevitationOffset(float tickDelta){
        //float val = 0.1F + MathHelper.sin((getWorld().getTime() + ticks) * 0.1F) * 0.01F;
        //MCCourseMod.LOGGER.info(this.ticks + " s");
        return LEVITATION_SPEED * (float) Math.sin((this.ticks + tickDelta) / LEVITATION_RANGE_DIVISOR);
    }

    // Synchronization // This is inside the blockEntity

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    @Override
    public void tick() {
        //if(this.world != null || !this.world.isClient)
        this.ticks++;
    }

    /* ADDING A SCREEN */
    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.mccourse.pedestal");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PedestalScreenHandler(syncId, playerInventory, this.pos);
    }

    @Override
    public World getEntityWorld() {
        return world;
    }

    @Override
    public Vec3d getEntityPos() {
        return Vec3d.of(new Vec3i(pos.getX(), pos.getY(), pos.getZ())) ;
    }

    @Override
    public float getBodyYaw() {
        return 0;
    }

    //@Override
    /*public static void tick(World world, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {

    }*/

}
