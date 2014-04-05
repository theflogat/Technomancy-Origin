package democretes.blocks.machines.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import democretes.blocks.TileTechnomancy;

public class TileMannequin extends TileTechnomancy implements IAspectContainer, IInventory{
	
	int amount;
	int maxAmount;
	Aspect aspect;
	Aspect aspectSuction;
	int facing;
	
	public String player = "";
	

	@Override
	public AspectList getAspects()  {
	    AspectList al = new AspectList();
	    if ((this.aspect != null) && (this.amount > 0)) {
	      al.add(this.aspect, this.amount);
	    }
	    return al;
	}	  

	@Override
	public int addToContainer(Aspect tag, int amount)  {
	  if (amount == 0) {
	    return amount;
	  }
	  if (((this.amount < this.maxAmount) && (tag == this.aspect)) || (this.amount == 0)) {
	    this.aspect = tag;
	    int added = Math.min(amount, this.maxAmount - this.amount);
	    this.amount += added;
	    amount -= added;
	  }
	  this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	  return amount;
	}
	  
	@Override
	public boolean takeFromContainer(Aspect tag, int amount)  {
		if ((this.amount >= amount) && (tag == this.aspect)) {
			this.amount -= amount;
			if (this.amount <= 0) {
				this.aspect = null;
			    this.amount = 0;
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amt) {
		if ((this.amount >= amt) && (tag == this.aspect)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean doesContainerContain(AspectList ot)  {
		for (Aspect tt : ot.getAspects()) {
			if ((this.amount > 0) && (tt == this.aspect)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean canUpdate()  {
	    return true;
	  }	
	
	@Override
	public boolean takeFromContainer(AspectList ot) {
	  return false;
	}	
	
	@Override
	public int containerContains(Aspect tag)  {
	  return 0;
	}
	
	@Override
	public boolean doesContainerAccept(Aspect tag) {
	  return false;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int stack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() {
		return "democretes.mannequin";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return (this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this) && (player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) < 64.0D);
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		Item stackItem = itemstack.getItem();
		if ((stackItem instanceof ItemArmor)) {
			ItemArmor armorItem = (ItemArmor)stackItem;
		    if (armorItem != null) {
		    	int armorType = armorItem.armorType;
		        if ((slot == 0) && (armorType == 0)) {
		        	return true;
		        }
		        if ((slot == 1) && (armorType == 1)) {
		        	return true;
		        }
		        if ((slot == 2) && (armorType == 2)) {
		        	return true;
		        }
		        if ((slot == 3) && (armorType == 3)) {
		        	return true;
		        }
		    }
		}
		return false;		    
	}

}
