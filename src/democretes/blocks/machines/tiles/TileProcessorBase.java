package democretes.blocks.machines.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import thermalexpansion.block.TEBlocks;
import democretes.blocks.TileTechnomancy;
import democretes.items.TMItems;

public class TileProcessorBase extends TileTechnomancy implements ISidedInventory {
	
	public ItemStack[] inventory = new ItemStack[2];
	public byte facing;
	public int time = 0;
	public int maxTime = 100;
	public String tagCompound;
	
	@Override
	public void updateEntity() {
		int f = 0;
		boolean oreTE = false;
		boolean oreVanilla = false;
		if((oreTE || oreVanilla) && this.inventory[0] != null) {
			if(!this.inventory[0].stackTagCompound.getBoolean(this.tagCompound)) {
				if(canProcess()) {
					do {
						if(this.inventory[0] != null) {
							this.time++;
						}else{
							return;
						}
					}while(time < maxTime);
					processStuff(f, oreTE, oreVanilla);
				}
			}else{
				return;
			}
		}
		if(canProcess() && this.inventory[0] != null) {
			for(int i = 0; i < TMItems.ores.length; i++) {
				if(this.inventory[0].getItem() == TMItems.ores[i].getItem()) {
					f = i;					
					oreTE = true;
					break;
				}
				if(this.inventory[0].itemID == Block.oreGold.blockID || this.inventory[0].itemID == Block.oreIron.blockID || this.inventory[0].itemID == TEBlocks.blockOre.blockID) {
					oreVanilla = true;
					break;
				}
			}
		}		
	}
	
	void processStuff(int f, boolean oreTE, boolean oreVanilla) {
		if(oreTE) {
			if(this.inventory[1] == null) {
				ItemStack stack = new ItemStack(TMItems.ores[f].getItem(), this.inventory[1].getItemDamage() + 1, 1);
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
				this.inventory[0].stackSize -= 1;
				if(this.inventory[0].stackSize == 0) {
					this.inventory[0] = null;
				}
			}else if(this.inventory[1].getItem() == TMItems.ores[f].getItem()) {
				if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
					this.inventory[1].stackSize += 1;
					this.inventory[0].stackSize -= 1;
					if(this.inventory[0].stackSize == 0) {
						this.inventory[0] = null;
					}
				}
			}
		}
		if(oreVanilla) {
			if(this.inventory[1] == null) {
				ItemStack stack = new ItemStack(getOreEquivalencies(this.inventory[0].itemID, this.inventory[0].getItemDamage()), 1, 1);
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
			}else if(this.inventory[1] != null) {
				if(getOreEquivalencies(this.inventory[0].itemID, this.inventory[0].getItemDamage()) == this.inventory[1].getItem()) {
					if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
						this.inventory[1].stackSize += 1;
						this.inventory[0].stackSize -= 1;
						if(this.inventory[0].stackSize == 0) {
							this.inventory[0] = null;
						}
					}
				}
			}
		}
	}
	
	Item getOreEquivalencies(int id, int meta) {
		if(id == Block.oreGold.blockID) {
			return TMItems.ores[0].getItem();
		}else if(id == Block.oreIron.blockID) {
			return TMItems.ores[1].getItem();
		}else if(id == TEBlocks.blockOre.blockID) {
			TMItems.ores[2 + meta].getItem();
		}
		return null;		
	}
	
	boolean canProcess() {
		return false;
	}	
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
	    this.facing = compound.getByte("Facing");
	    this.time = compound.getInteger("Time");
	    
	   	NBTTagList nbttaglist = compound.getTagList("Items");
	    this.inventory = new ItemStack[getSizeInventory()];
	    for (int i = 0; i < nbttaglist.tagCount(); i++) {
	    	NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
	    	byte b0 = nbttagcompound.getByte("Slot");
	    	if ((b0 >= 0) && (b0 < this.inventory.length)) {
	    		this.inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
	    	}
	    }
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		compound.setByte("Facing", this.facing);
		compound.setInteger("Time", this.time);
		
		NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < this.inventory.length; i++) {
				if (this.inventory[i] != null) {
					NBTTagCompound nbttagcompound = new NBTTagCompound();
					nbttagcompound.setByte("Slot", (byte)i);
					this.inventory[i].writeToNBT(nbttagcompound);
					nbttaglist.appendTag(nbttagcompound);
				}
			}
		compound.setTag("Items", nbttaglist);
	}	

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack stack = this.inventory[i].copy();
		stack.stackSize = this.inventory[i].stackSize - j;
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.inventory[i] = itemstack.copy();	
	}

	@Override
	public String getInvName() {
		return "processorInv" + this.tagCompound;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int i) {		
		return new int[] { 1, 0, 1, 1, 1, 1};
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		if(j == 1 && stack == this.inventory[0] && i == 0) {
			return true;
		}else if(stack == this.inventory[1]){
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(i == 0 && j == 1) {
			return true;
		}
		return false;
	}

}
