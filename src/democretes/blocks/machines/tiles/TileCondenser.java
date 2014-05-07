package democretes.blocks.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileCondenser extends TileMachineBase implements IEssentiaTransport, IAspectContainer {

	public Aspect aspect;
	public int amount = 0;
	public int maxAmount = 64;
	
	@Override
	public void updateEntity() {
		if(this.energyStorage.getEnergyStored() >= 10000000 && this.amount < this.maxAmount) {
			this.energyStorage.extractEnergy(10000000, false);
			this.addToContainer(Aspect.ENERGY, 1);
		}
		fill();
	}
	
	void fill() {
		TileEntity te = null;
		IEssentiaTransport ic = null;
		for (int y = 0; y <= 1; y++) {
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if ((dir != ForgeDirection.DOWN) && ((y != 0) || (dir != ForgeDirection.UP))) {
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
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound)  {
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}
		compound.setShort("Amount", (short)this.amount);
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
		return true;
	}

	@Override
	public boolean canInputFrom(ForgeDirection face) {
		return true;
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
		return null;
	}

	@Override
	public int getSuctionAmount(ForgeDirection face) {
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

	
	
}
