package democretes.blocks.gui.container;

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
				
	    addSlotToContainer(new Slot(processor, 0, 49, 27));
	    addSlotToContainer(new Slot(processor, 1, 106, 27));
	    
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
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		Slot slot = getSlot(i);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			ItemStack result = stack.copy();
			
			if (i >= 36) {
				if (!mergeItemStack(stack, 0, 36, false)) {
					return null;
				}
			}else if(!mergeItemStack(stack, 36, 36 + processor.getSizeInventory(), false)) {
				return null;
			}
			
			if (stack.stackSize == 0) {
				slot.putStack(null);
			}else{
				slot.onSlotChanged();
			}
			
			slot.onPickupFromSlot(player, stack);
			
			return result;
		}
		
		return null;
	}

}
