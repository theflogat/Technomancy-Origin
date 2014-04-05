package democretes.blocks.machines.tiles;

import java.util.ArrayList;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileInfusionMatrix;
import cofh.util.FluidHelper;
import cofh.util.fluid.FluidTankAdv;
import democretes.blocks.TileTechnomancy;

public class TileFluxLamp extends TileTechnomancy implements IAspectContainer, IEssentiaTransport, IFluidHandler{
	
	int amount = 0;
	int maxAmount = 32;
	Aspect aspect;
	public FluidTankAdv tank = new FluidTankAdv(1000);	
	Aspect aspectSuction;
	boolean stabilize = true;
	public boolean placed = false;
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setShort("Amount", (short)this.amount);
		compound.setBoolean("Stabilize", this.stabilize);
		if (this.aspect != null) {
			compound.setString("Aspect", this.aspect.getTag());
		}	
		compound.setBoolean("Placed", this.placed);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.amount = compound.getShort("Amount");
		this.aspect = Aspect.getAspect(compound.getString("Aspect"));
		this.stabilize = compound.getBoolean("Stabilize");
		this.placed = compound.getBoolean("Placed");
	}

	@Override
	public void updateEntity() {
		if(!this.worldObj.isRemote) {
			TileEntity tile = getMatrix();
			if(tile == null) {
				return;
			}
			TileInfusionMatrix matrix = (TileInfusionMatrix)tile;

			if(!matrix.crafting) {
				this.stabilize = true;
			}	
			if(matrix.instability > 0 && this.stabilize) {
				for(int i = 0; i < 5; i++) {					
					if( this.amount >= 5 && this.tank.getSpace() >= 200 && this.stabilize) {
						this.takeFromContainer(this.aspect, 5);
						matrix.instability -=1;
						this.tank.fill(FluidRegistry.getFluidStack(ConfigBlocks.FLUXGOO.getName(), 200), true);
					}else{
						break;
					}						
				}
				this.stabilize = false;
			}					
			if((this.amount < this.maxAmount)) {
				fill();
			}
		}		
	}
	
	TileEntity getMatrix() {
		for(int yy = -5; yy < 5; yy++) {
			for(int xx = -10; xx < 10; xx++) {
				for(int zz = -10; zz < 10; zz++) {
					if(xx != 0 && zz != 0 && yy != 0) {
						TileEntity te = this.worldObj.getBlockTileEntity(this.xCoord + xx, this.yCoord + yy, this.zCoord + zz);
						if(te instanceof TileInfusionMatrix) {
							return te;
						}
					}
				}
			}
		}
		return null;
	}	
	
	void fill() {
		TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
		if (te != null) {
			IEssentiaTransport ic = (IEssentiaTransport)te;
			if (!ic.canOutputTo(ForgeDirection.DOWN)) {
				return;
			}
			Aspect ta = null;
			if ((this.aspect != null) && (this.amount > 0)) {
				ta = this.aspect;
			} else if ((ic.getEssentiaAmount(ForgeDirection.DOWN) > 0) && 
					(ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP)) && (getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction())) {
				ta = ic.getEssentiaType(ForgeDirection.DOWN);
			}
			if ((ta != null) && (ic.getSuctionAmount(ForgeDirection.DOWN) < getSuctionAmount(ForgeDirection.UP))) {
				addToContainer(ta, ic.takeVis(ta, 1));
			}
		}
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
	public int getMinimumSuction() {
	  return 128;
	}
	
	@Override
	public int takeVis(Aspect aspect, int amount) {
	  return takeFromContainer(aspect, amount) ? amount : 0;
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
	public boolean renderExtendedTube() {
	  return true;
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
	public boolean isConnectable(ForgeDirection face) {
	  return face == ForgeDirection.UP;
	}
	
	@Override
	public boolean canInputFrom(ForgeDirection face) {
	  return face == ForgeDirection.UP;
	}
	
	@Override
	public boolean canOutputTo(ForgeDirection face) {
	  return false;
	}

	@Override
	public Aspect getSuctionType(ForgeDirection face) {
		if(this.amount < this.maxAmount) {
			this.aspectSuction = Aspect.ORDER;
		}
		return this.aspectSuction;
	}

	@Override
	public int getSuctionAmount(ForgeDirection loc) {
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
	public void setSuction(Aspect aspect, int amount) {	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		FluidStack stack = FluidRegistry.getFluidStack(fluid.getName(), 200);
		int f = FluidHelper.insertFluidIntoAdjacentFluidHandler(this, from.ordinal(), stack, false);
		if(f == 200) {
			return true;
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { this.tank.getInfo() };
	}
	
	
}
