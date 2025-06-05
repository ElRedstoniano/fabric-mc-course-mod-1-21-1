package net.kaupenjoe.mccourse.block.entity.custom;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.entity.ModBlockEntities;
import net.kaupenjoe.mccourse.util.TickableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PedestalBlockEntity extends BlockEntity implements Inventory, /*BlockEntityTicker<PedestalBlockEntity>,*/ TickableBlockEntity {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public int ticks = 0;
    //private float rotation = 0;
    //private float levitationOffset = 0;
    private final float ROTATION_SPEED = 2F;
    private final float LEVITATION_SPEED = 0.05F;
    private final float LEVITATION_RANGE_DIVISOR = 8F;

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
        ItemStack stack = inventory.get(slot);
        stack.decrement(amount);
        //return Inventories.removeStack(inventory, slot); // también sirve
        return inventory.set(slot, stack);
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
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
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

    //@Override
    /*public static void tick(World world, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {

    }*/
}
