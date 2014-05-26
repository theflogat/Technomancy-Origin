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
	public boolean active;
	
	 public static ItemStack[] ores = { 
	    	new ItemStack(TMItems.processedIron), 
	    	new ItemStack(TMItems.processedGold), 
	    	new ItemStack(TMItems.processedCopper), 
	    	new ItemStack(TMItems.processedTin), 
	    	new ItemStack(TMItems.processedSilver), 
	    	new ItemStack(TMItems.processedLead), 
	    	new ItemStack(TMItems.processedNickel)};

	boolean ore1 = false;
	boolean ore2 = false;
	int f;
	
	@Override
	public void updateEntity() {
		if(this.inventory[0] == null) {
			this.time = 0;
		}
		if((ore1 || ore2) && this.inventory[0] != null) {
			if(this.inventory[0].getTagCompound() == null) {
				this.inventory[0].stackTagCompound = new NBTTagCompound();
				this.inventory[0].stackTagCompound.setBoolean(this.tagCompound, false);
			}
			if(this.inventory[0].stackTagCompound.getBoolean(this.tagCompound)) {
				return;
			}
			if(canProcess()) {
				if(this.inventory[0] != null) {
					this.active = true;
					this.time++;
				}else{
					return;
				}
				if(this.time >= this.maxTime) {
					getFuel();
					processStuff(f, ore1, ore2);
					this.time = 0;
				}else{
					return;
				}
			}
		}
		this.active = false;
		if(canProcess() && this.inventory[0] != null) {
			String name = this.inventory[0].getUnlocalizedName();
			if(name.equals(Block.oreGold.getUnlocalizedName()) || name.equals(Block.oreIron.getUnlocalizedName())  || name.equals(TEBlocks.blockOre.getUnlocalizedName())) {
				ore2 = true;
			}
			for(int i = 0; i < this.ores.length; i++) {
				if(this.inventory[0].getItem() == this.ores[i].getItem()) {
					if(this.inventory[0].getItemDamage() < 5) {
						f = i;					
						ore1 = true;
						break;
					}
				}
			}
		}
	}
	
	void processStuff(int j, boolean ore1, boolean ore2) {
		if(ore1) {
			if(this.inventory[1] == null) {
				ItemStack stack = new ItemStack(this.ores[j].getItem(), this.inventory[1].getItemDamage() + 1, 1);
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
				this.inventory[0].stackSize -= 1;
				if(this.inventory[0].stackSize == 0) {
					this.inventory[0] = null;
				}
			}else if(this.inventory[1].getItem() == this.ores[j].getItem()) {
				if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
					this.inventory[1].stackSize += 1;
					this.inventory[1].stackTagCompound.setBoolean(this.tagCompound, true);
					this.inventory[0].stackSize -= 1;
					if(this.inventory[0].stackSize == 0) {
						this.inventory[0] = null;
					}
				}
			}
		}
		if(ore2) {
			if(this.inventory[1] == null) {
				ItemStack stack = new ItemStack(getOreEquivalencies(this.inventory[0].getUnlocalizedName(), this.inventory[0].getItemDamage()), 1, 0);
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
				this.inventory[0].stackSize -= 1;
				if(this.inventory[0].stackSize == 0) {
					this.inventory[0] = null;
				}
			}else if(this.inventory[1] != null) {
				if(getOreEquivalencies(this.inventory[0].getUnlocalizedName(), this.inventory[0].getItemDamage()) == this.inventory[1].getItem()) {
					if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
						this.inventory[1].stackSize += 1;
						this.inventory[1].stackTagCompound.setBoolean(this.tagCompound, true);
						this.inventory[0].stackSize -= 1;
						if(this.inventory[0].stackSize == 0) {
							this.inventory[0] = null;
						}
					}
				}
			}
		}
	}
	
	Item getOreEquivalencies(String name, int meta) {
		if(name.equals(Block.oreIron.getUnlocalizedName())) {
			return this.ores[0].getItem();
		}else if(name.equals(Block.oreGold.getUnlocalizedName())) {
			return this.ores[1].getItem();
		}else if(name.equals(TEBlocks.blockOre.getUnlocalizedName())) {
			this.ores[2 + meta].getItem();
		}
		return null;		
	}
	
	boolean canProcess() {
		return true;
	}	
	
	void getFuel() {}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
	    this.facing = compound.getByte("Facing");
	    this.time = compound.getInteger("Time");
	    this.active = compound.getBoolean("Active");
	    
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
		compound.setBoolean("Active", this.active);
		
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
		this.inventory[i] = itemstack;		
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
