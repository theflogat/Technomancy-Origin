package democretes.blocks.machines.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thermalexpansion.block.TEBlocks;
import democretes.blocks.TileTechnomancy;
import democretes.items.TMItems;

public class TileTCProcessor extends TileTechnomancy implements IEssentiaTransport, IAspectContainer, IInventory {
	
	public ItemStack[] inventory = new ItemStack[2];
	public Aspect aspect;
	public int amount = 0;
	public int maxAmount = 64;
	public Aspect suction = Aspect.FIRE;
	public byte facing;
	
	@Override
	public void updateEntity() {
		if(this.amount > 16 && this.inventory[0] != null) {
			int f = 0;
			int j = 0;
			for(int i = 0; i < TMItems.ores.length; i++) {
				if(this.inventory[i].getItem() == TMItems.ores[i].getItem()) {
					f = i + 1;
					break;
				}
				if(this.inventory[i].itemID == Block.oreGold.blockID || this.inventory[i].itemID == Block.oreIron.blockID || this.inventory[i].itemID == TEBlocks.blockOre.blockID) {
					j = i + 1;
					break;
				}
			}
			if(f != 0) {
				if(this.inventory[1] == null) {
					ItemStack stack = new ItemStack(TMItems.ores[f - 1].getItem(), this.inventory[1].getItemDamage() + 1, 1);
					stack.stackTagCompound.setBoolean("Thaumcraft", true);
					this.inventory[1] = stack.copy();
					this.takeFromContainer(this.aspect, 4);
					this.inventory[0].stackSize -= 1;
					if(this.inventory[0].stackSize == 0) {
						this.inventory[0] = null;
					}
				}else if(this.inventory[1].getItem() == TMItems.ores[f - 1].getItem()) {
					if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
						this.inventory[1].stackSize += 1;
						this.takeFromContainer(this.aspect, 4);
						this.inventory[0].stackSize -= 1;
						if(this.inventory[0].stackSize == 0) {
							this.inventory[0] = null;
						}
					}
				}
			}
			if(j != 0) {
				if(this.inventory[1] == null) {
					ItemStack stack = new ItemStack(getOreEquivalencies(this.inventory[0].itemID, this.inventory[0].getItemDamage()), 1, 1);
					stack.stackTagCompound.setBoolean("Thaumcraft", true);
					this.inventory[1] = stack.copy();					
					this.takeFromContainer(this.aspect, 4);
				}else if(this.inventory[1] != null) {
					if(getOreEquivalencies(this.inventory[0].itemID, this.inventory[0].getItemDamage()) == this.inventory[1].getItem()) {
						if(this.inventory[1].stackSize < this.inventory[1].getMaxStackSize()) {
							this.inventory[1].stackSize += 1;
							this.takeFromContainer(this.aspect, 4);
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
	
	void fill() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.getOrientation(this.facing)) && (dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
					te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord + y, this.zCoord, dir);
					if (te != null) {
						ic = (IEssentiaTransport)te;
						Aspect ta = ic.getEssentiaType(dir.getOpposite());
						if ((ic.getEssentiaAmount(dir.getOpposite()) > 0) && (ic.getSuctionAmount(dir.getOpposite()) < getSuctionAmount(null)) && (getSuctionAmount(null) >= ic.getMinimumSuction())) {
							addToContainer(ta, ic.takeVis(ta, 1));
							return;
						}
					}
				}
			}
		}
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound)  {
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
		this.amount = compound.getShort("Amount");
	    this.facing = compound.getByte("Facing");
	    
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
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
		compound.setShort("Amount", (short)this.amount);
		compound.setByte("Facing", this.facing);
		
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
		if(this.amount >= amount && this.aspect == tag) {
			this.amount -= amount;
			if(this.amount <= 0) {
				this.aspect = null;
				this.amount = 0;					
			}
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			return true;			
		}
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
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
	public int containerContains(Aspect tag) {
		return 0;
	}

	@Override
	public boolean isConnectable(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return (face != ForgeDirection.getOrientation(this.facing));
	}

	@Override
	public boolean canOutputTo(ForgeDirection face) {
		return false;
	}

	@Override
	public void setSuction(Aspect aspect, int amount) {	}

	@Override
	public int takeVis(Aspect aspect, int amount) {
		  return takeFromContainer(aspect, amount) ? amount : 0;
	}

	@Override
	public int getMinimumSuction() {
		  return 128;
	}

	@Override
	public boolean renderExtendedTube() {
		return true;
	}
	

	@Override
	public void setAspects(AspectList aspects) {	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		return this.suction;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
		if (this.amount < this.maxAmount){
			return 128;
		}
	return 0;
	}

	@Override
	public int addVis(Aspect aspect, int amount) {
		return amount - addToContainer(aspect, amount);
	}

	@Override
	public Aspect getEssentiaType(ForgeDirection face) {
		return this.aspect;
	}

	@Override
	public int getEssentiaAmount(ForgeDirection face) {
		return this.amount;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		return false;
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
		return "processorInvTC";
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
		return false;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0 && OreDictionary.getOreID(stack) != -1) {
			return true;
		}
		return false;
	}

}
