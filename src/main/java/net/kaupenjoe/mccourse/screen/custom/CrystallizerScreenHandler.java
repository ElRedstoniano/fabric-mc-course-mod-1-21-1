package net.kaupenjoe.mccourse.screen.custom;

import net.kaupenjoe.mccourse.MCCourseMod;
import net.kaupenjoe.mccourse.block.entity.custom.CrystallizerBlockEntity;
import net.kaupenjoe.mccourse.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class CrystallizerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final CrystallizerBlockEntity blockEntity;

    public CrystallizerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory,inventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(2));
    }

    public CrystallizerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate arrayPropertyDelegate){
        super(ModScreenHandlers.CRYSTALLIZER_SCREEN_HANDLER, syncId);
        checkSize((Inventory) blockEntity, 4);
        this.inventory = (Inventory) blockEntity;
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = (CrystallizerBlockEntity) blockEntity;

        this.addSlot(new Slot(inventory, 0, 8, 62));
        this.addSlot(new Slot(inventory, 1, 54, 34){
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                super.onTakeItem(player, stack);
                //if (player.getWorld().isClient()){
                    //MinecraftClient.getInstance().getNetworkHandler().sendPacket( new InventoryS2CPacket(syncId, 0, ((CrystallizerBlockEntity) blockEntity).getItems(), stack));
                //player.networkHandler.sendPacket(new InventoryS2CPacket(syncId, 0, ((CrystallizerBlockEntity) blockEntity).getItems(), stack));
                /*((ClientPlayerEntity) player).networkHandler.sendPacket(
                        new InventoryS2CPacket(player.currentScreenHandler.syncId, 0, ((CrystallizerBlockEntity) blockEntity).getItems(), stack));*/
                //}
                //MCCourseMod.LOGGER.info(stack.toString());
            }

        });
        this.addSlot(new Slot(inventory, 2, 104, 34));
        this.addSlot(new Slot(inventory, 3, 152, 62));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(arrayPropertyDelegate);
    }

    public boolean isCrafting(){
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledArrowProgress(){
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1); // Max Progress
        int arrowPixelSize = 24; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? (progress * arrowPixelSize / maxProgress) : 0;
    }

    public int getScaledCrystalProgress(){
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1); // Max Progress
        int arrowPixelSize = 16; // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? (progress * arrowPixelSize / maxProgress) : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if(slot != null && slot.hasStack()){
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if(invSlot < this.inventory.size()){ // Si se está en el slot del pedestal
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), true)){
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory){
        for(int i = 0; i < 3; i++){
            for(int l = 0; l < 9; l++){
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory){
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
