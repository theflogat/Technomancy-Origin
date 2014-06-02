package democretes.blocks.machines.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import thermalexpansion.block.simple.BlockOre;
import democretes.blocks.TileTechnomancy;
import democretes.items.ItemProcessedOre;
import democretes.items.TMItems;

public class TileProcessorBase extends TileTechnomancy implements ISidedInventory {
	
	public ItemStack[] inventory = new ItemStack[2];
	public byte facing;
	public int time = 0;
	public int maxTime = 100;
	public String tagCompound;
	public boolean active;
	public int multiplier = 0;
	
	public static ItemStack[] pureOres = { 
	   	new ItemStack(TMItems.processedIron), 
	   	new ItemStack(TMItems.processedGold), 
	   	new ItemStack(TMItems.processedCopper), 
	   	new ItemStack(TMItems.processedTin), 
	   	new ItemStack(TMItems.processedSilver), 
	  	new ItemStack(TMItems.processedLead), 
	   	new ItemStack(TMItems.processedNickel)};

	String[] processors = {"Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery", "Totemic" }; 
	 
	boolean ore1 = false;
	boolean ore2 = false;
	int f;
	
	@Override
	public void updateEntity() {
		if(this.inventory[0] == null) {
			this.time = 0;
		}
		if((ore1 || ore2) && this.inventory[0] != null) {
			if(this.inventory[0].getTagCompound() != null) {
				if(this.inventory[0].stackTagCompound.getBoolean(this.tagCompound)) {
					return;
				}
			}
			if(canProcess()) {
				if(this.inventory[1] != null) {
					if(ore2) {
						if(this.inventory[1].getItem().itemID != getOreEquivalencies(OreDictionary.getOreID(this.inventory[0])).itemID) {
							this.active = false;
							return;
						}
					}
					if(ore1) {
						if(this.inventory[0].getItem().itemID != this.inventory[1].getItem().itemID) {
							this.active = false;
							return;
						}
					}
				}
				if(this.inventory[0] != null) {
					this.active = true;
					this.time++;
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}else{
					return;
				}
				if(this.time >= this.maxTime) {
					getFuel();
					processStuff(f, ore1, ore2);
					this.time = 0;
					return;
				}else{
					return;
				}
			}
		}
		this.active = false;
		if(canProcess() && this.inventory[0] != null) {
			int id = OreDictionary.getOreID(this.inventory[0]);
			if(!OreDictionary.getOres(id).isEmpty()) {
				ore2 = true;
				this.multiplier = this.inventory[0].getItemDamage() + 1;
			}
			for(int i = 0; i < this.pureOres.length; i++) {
				if(this.inventory[0].getItem() == this.pureOres[i].getItem()) {
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
				ItemStack stack = new ItemStack(this.pureOres[j].getItem(), 1, this.inventory[0].getItemDamage() + 1);
				stack.stackTagCompound = new NBTTagCompound();
				for(int i = 0; i < processors.length; i++) {
					if(this.inventory[0].stackTagCompound.hasKey(this.processors[i])) {
						stack.stackTagCompound.setBoolean(this.processors[i], this.inventory[0].stackTagCompound.getBoolean(processors[i]));
					}
				}
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
				if(this.inventory[0] != null) {
					this.inventory[0].stackSize -= 1;
					if(this.inventory[0].stackSize == 0) {
						this.inventory[0] = null;
					}
				}
			}else if(this.inventory[1].getItem() == this.pureOres[j].getItem()) {
				if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
					this.inventory[1].stackSize += 1;
					if(this.inventory[0] != null) {
						this.inventory[0].stackSize -= 1;
						if(this.inventory[0].stackSize == 0) {
							this.inventory[0] = null;
						}
					}
				}
			}
		}
		if(ore2) {
			if(this.inventory[1] == null) {
				ItemStack stack = new ItemStack(getOreEquivalencies(OreDictionary.getOreID(this.inventory[0])), 1, 0);
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setBoolean(this.tagCompound, true);
				this.inventory[1] = stack.copy();
				if(this.inventory[0] != null) {
					this.inventory[0].stackSize -= 1;
					if(this.inventory[0].stackSize == 0) {
						this.inventory[0] = null;
					}
				}
			}else if(this.inventory[1] != null) {
				if(getOreEquivalencies(OreDictionary.getOreID(this.inventory[0])) == this.inventory[1].getItem()) {
					if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
						this.inventory[1].stackSize += 1;
						this.inventory[1].stackTagCompound.setBoolean(this.tagCompound, true);
						if(this.inventory[0] != null) {
							this.inventory[0].stackSize -= 1;
							if(this.inventory[0].stackSize == 0) {
								this.inventory[0] = null;
							}
						}
					}
				}
			}
		}
	}
	
	Item getOreEquivalencies(int id) {
		if(OreDictionary.getOreName(id).equals("oreIron")) {
			return this.pureOres[0].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreGold")) {
			return this.pureOres[1].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreCopper")) {
			return this.pureOres[2].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreTin")) {
			return this.pureOres[3].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreSilver")) {
			return this.pureOres[4].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreLead")) {
			return this.pureOres[5].getItem();
		}else if(OreDictionary.getOreName(id).equals("oreNickel")) {
			return this.pureOres[6].getItem();
		}
		return null;		
	}
	
	boolean canProcess() {
		return false;
	}	
	
	void getFuel() {}
	
	@SideOnly(Side.CLIENT)
	public int getTimeScaled(int j) {		
		return this.time * j / this.maxTime;
	}
	
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
	    this.facing = compound.getByte("Facing");
	    this.time = compound.getInteger("Time");
	    this.active = compound.getBoolean("Active");	    
	}	
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		compound.setByte("Facing", this.facing);
		compound.setInteger("Time", this.time);
		compound.setBoolean("Active", this.active);		 
	}	

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		NBTTagList nbttaglist = new NBTTagList();
	    for (int i = 0; i < this.inventory.length; ++i) {
	        if (this.inventory[i] != null) {
	            NBTTagCompound compound1 = new NBTTagCompound();
	            compound1.setByte("Slot", (byte)i);
	            this.inventory[i].writeToNBT(compound1);
	            nbttaglist.appendTag(compound1);
	        }
	    }
	    compound.setTag("Items", nbttaglist);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList nbttaglist = compound.getTagList("Items");
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound compound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = compound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.inventory.length)            {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(compound1);
            }
        }
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
		if(this.inventory[i] != null) {
			if(this.inventory[i].stackSize <= j) {
				ItemStack stack = this.inventory[i];
				this.inventory[i] = null;
				return stack;
			}
			ItemStack stack = this.inventory[i].splitStack(j);
		    if (this.inventory[i].stackSize == 0) {
		    	this.inventory[i] = null;
		    }
		    return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.inventory[i] != null) {
			return this.inventory[i];
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		this.inventory[i] = stack;
		if(stack != null) {
			if(stack.stackSize > this.getInventoryStackLimit()) {
				this.inventory[i].stackSize = this.getInventoryStackLimit();
			}
		}
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
	public boolean isUseableByPlayer(EntityPlayer player) {
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
		if(i == 0 || i == 1) {
			return new int[] {0};
		}
		return new int[] {1};
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
