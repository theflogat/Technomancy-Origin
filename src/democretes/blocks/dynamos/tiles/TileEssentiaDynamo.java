package democretes.blocks.dynamos.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyStorage;

public class TileEssentiaDynamo extends TileDynamoBase implements IAspectContainer, IEssentiaTransport{

	private int amount;
	private int maxAmount = 64;
	private static Aspect aspect = null;
	public int count;
	
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.amount = compound.getShort("Amount");
		this.fuelRF = compound.getInteger("RF");
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
		this.facing = compound.getByte("Facing");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setShort("Amount", (short)this.amount);
		compound.setInteger("RF", fuelRF);	
		compound.setByte("Facing", this.facing);
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
	}
	
	@Override
	protected boolean canGenerate() {
		if (amount > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected void generate() {
		if (this.fuelRF <= 0) {
			this.fuelRF += getFuelEnergy();
			this.takeFromContainer(this.aspect, 1);
		}
		int energy = calcEnergy();
		this.energyStorage.modifyEnergyStored(energy);
		this.fuelRF -= energy;
	}
	
	public static int getFuelEnergy() {				
		return ((getAspectFuel(aspect))*3/4);
	}
	
	public static int getAspectFuel(Aspect aspect) {
		if (aspect == Aspect.FIRE || aspect == Aspect.ENERGY) {
			return 32000;}
		if (aspect == Aspect.AIR ||  aspect == Aspect.WATER || aspect == Aspect.ENTROPY || aspect == Aspect.ORDER) {
			return 12000;}
		if (aspect == Aspect.AURA || aspect == Aspect.TAINT || aspect == Aspect.MAGIC  || aspect == Aspect.ELDRITCH ) {
			return 24000;}
		if (aspect == Aspect.MECHANISM || aspect == Aspect.METAL || aspect == Aspect.MOTION || aspect == Aspect.TOOL || aspect == Aspect.TRAP || 
				aspect == Aspect.MINE || aspect == Aspect.CRAFT || aspect == Aspect.TRAVEL) {
			return 16000;}
		if (aspect == Aspect.STONE || aspect == Aspect.CRYSTAL || aspect == Aspect.ICE || aspect == Aspect.EARTH || aspect == Aspect.LIGHT){
			return 800;	}
		if (aspect == Aspect.MAN || aspect == Aspect.SENSES | aspect == Aspect.HEAL || aspect == Aspect.HARVEST || aspect == Aspect.HUNGER ||
				aspect == Aspect.FLIGHT || aspect == Aspect.DEATH || aspect == Aspect.BEAST ||  aspect == Aspect.POISON|| aspect == Aspect.MIND || 
				aspect == Aspect.SOUL || aspect == Aspect.UNDEAD || aspect == Aspect.WEAPON || aspect == Aspect.WEATHER || aspect == Aspect.UNDEAD) {
			return 12000;}
		if ( aspect == Aspect.TREE || aspect == Aspect.SLIME || aspect == Aspect.SEED || aspect == Aspect.PLANT || aspect == Aspect.CROP || 
				aspect == Aspect.CLOTH || aspect == Aspect.VOID || aspect == Aspect.EXCHANGE || aspect == Aspect.FLESH) { 
			return 8000;}		
		return 0;
	}

	@Override
	public AspectList getAspects() {
	    AspectList al = new AspectList();
	    if ((this.aspect != null) && (this.amount > 0)) {
	      al.add(this.aspect, this.amount);
	    }
	    return al;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if ((!this.worldObj.isRemote) && (++this.count % 10 == 0) && (this.amount < this.maxAmount)) {
		  fill();
		}
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

	ForgeDirection findTube() {
		if (!this.worldObj.isRemote) {
			if (ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP) != null) {
					return ForgeDirection.UP;				
			}else if (ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.DOWN) !=null) {
				return ForgeDirection.DOWN;
			}else if(ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.EAST) != null) {
				return ForgeDirection.EAST;				
			}else if(ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.WEST) != null) {
				return ForgeDirection.WEST;
			}else if(ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.NORTH) != null) {
				return ForgeDirection.NORTH;
			}else if(ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.SOUTH) != null) {
				return ForgeDirection.SOUTH;
			}
		}
		return null;
	}
	
	@Override
	public int addToContainer(Aspect tag, int am) {
		if (am == 0) {
		    return am;
		  }
		if (((this.amount < this.maxAmount ) && (tag == this.aspect)) || (this.amount == 0)) {
		    this.aspect = tag;
		    int added = Math.min(am, this.maxAmount - this.amount);
		    this.amount += added;
		    am -= added;
		  }
		  this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		  return am;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		if ((this.amount >= amount) && (tag == this.aspect))
		  {
		    this.amount -= amount;
		    if (this.amount <= 0)  {
		      this.aspect = null;
		      this.amount = 0;
		    }
		    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		    return true;
		  }
		  return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		if ((this.amount >= amount) && (tag == this.aspect)) {
		    return true;
		  }
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		for (Aspect tt : ot.getAspects()) {
		    if ((this.amount > 0) && (tt == this.aspect)) {
		      return true;
		    }
		  }
		  return false;
	}	

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
	public boolean isConnectable(ForgeDirection face) {
		return true;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
	}
	
	@Override
	public boolean doesContainerAccept(Aspect tag) {return false;}

	@Override
	public int containerContains(Aspect tag) {return 0;}
	
	@Override
	public boolean takeFromContainer(AspectList ot) {return false;}

	@Override
	public boolean canOutputTo(ForgeDirection face) {return false;}
	
	@Override
	public void setAspects(AspectList aspects) {}
	
	@Override
	public void setSuction(Aspect aspect, int amount) {}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		if(this.amount > 0) {
			return this.aspect;
		}
		return null;
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
	

}
