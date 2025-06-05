package net.kaupenjoe.mccourse.screen.custom;

import net.kaupenjoe.mccourse.screen.ModScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class PedestalScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public PedestalScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(blockPos)); // Client
    }
    public PedestalScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity) {
        super(ModScreenHandlers.PEDESTAL_SCREEN_HANDLER, syncId); // BlockEntity from Server
        checkSize((Inventory) blockEntity, 1);
        this.inventory = (Inventory) blockEntity;

        this.addSlot(new PedestalScreenHandler.PedestalHolderSlot(inventory, 0, 80, 35)); // Las coordenadas empiezan desde la
        //esquina superior izquierda, y se suma un desfase en x e y para contar desde donde empieza el nuevo slot

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
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

    public class PedestalHolderSlot  extends Slot {

        public PedestalHolderSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public int getMaxItemCount() {
            return 1;
        }
    }
}
