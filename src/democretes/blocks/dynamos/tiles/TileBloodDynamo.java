package democretes.blocks.dynamos.tiles;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.ModBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.util.FluidHelper;
import cofh.util.fluid.FluidTankAdv;

public class TileBloodDynamo extends TileDynamoBase implements IFluidHandler {

	public FluidTankAdv tank = new FluidTankAdv(5000);	

	
	@Override
	protected boolean canGenerate() {
		if(this.tank.getFluid() != null) {
			if(this.tank.getFluid().amount > 100) {
				return true;
			}		
		}
		return false;	
	}	
	
	@Override
	protected void generate() {
		if (this.fuelRF <= 0) {
			this.fuelRF += 10000;
			this.tank.drain(100, true);
		}
		int energy = calcEnergy();
		this.energyStorage.modifyEnergyStored(energy);
		this.fuelRF -= energy;
	}


	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if ((resource == null) || ((from != ForgeDirection.UNKNOWN) && (from.ordinal() == this.facing))) {
			return 0;
		}
		if(resource.getFluid() == AlchemicalWizardry.lifeEssenceFluid) {
			return this.tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if ( ((from != ForgeDirection.UNKNOWN) && (from.ordinal() == this.facing))) {
			return null;
		}
		if(this.tank.getFluid() != null) {
			return this.tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from.ordinal() != this.facing;
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
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.fuelRF = compound.getInteger("RF");
		this.facing = compound.getByte("Facing");
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setInteger("RF", fuelRF);	
		compound.setByte("Facing", this.facing);
	}
	

}
