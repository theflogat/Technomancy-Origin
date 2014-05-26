package democretes.blocks.gui.container;

import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import thermalexpansion.util.crafting.FurnaceManager;
import democretes.blocks.machines.tiles.TileTCProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerProcessorTC extends Container {

	private TileTCProcessor processor;
	
	public ContainerProcessorTC(InventoryPlayer inventory, TileTCProcessor processor) {
		this.processor = processor;
				
	    addSlotToContainer(new Slot(processor, 0, 50, 27));
	    addSlotToContainer(new Slot(processor, 1, 107, 27));
	    
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
		    }
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
		}
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return processor.isUseableByPlayer(entityplayer);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i)  {
		ItemStack stack = null;
	    Slot slot = (Slot)this.inventorySlots.get(i);
	    
	    int invTile = this.processor.inventory.length;
	    int invPlayer = invTile + 27;
	    int invFull = invTile + 36;
	    if ((slot != null) && (slot.getHasStack()))    {
	    	ItemStack stackInSlot = slot.getStack();
	    	stack = stackInSlot.copy();
	    	if (i == 1)      {
	    		if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
	    			return null;
	    		}
	    		slot.onSlotChange(stackInSlot, stack);
	    	}else if (i != 0) {
	    		if (processor.isItemValidForSlot(0, (stackInSlot))) {
	    			if (!mergeItemStack(stackInSlot, 0, 1, false)) {
	    				return null;
	    			}
	    		}else if ((i >= invTile) && (i < invPlayer)) {
	    			if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
	    				return null;
	    			}
	    		}else if ((i >= invPlayer) && (i < invFull) && (!mergeItemStack(stackInSlot, invTile, invPlayer, false))) {
	    			return null;
	    		}
	    	}else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
	    		return null;
	    	}
	    	if (stackInSlot.stackSize == 0) {
	    		slot.putStack((ItemStack)null);
	    	}else{
	    		slot.onSlotChanged();
	    	}
	    	if(stackInSlot.stackSize == stack.stackSize) {
	    		return null;
	    	}
	    	slot.onPickupFromSlot(player, stackInSlot);
	    }
	    return stack;
	}

}
