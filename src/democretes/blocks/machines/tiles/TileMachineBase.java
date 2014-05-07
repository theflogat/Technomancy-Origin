package democretes.blocks.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import democretes.blocks.TileTechnomancy;

public class TileMachineBase extends TileTechnomancy implements IEnergyHandler, IEnergyStorage {
	
	EnergyStorage energyStorage;
	
	public int energy;
	public int capacity;
	public int maxReceive;
	public int maxExtract = this.maxReceive;
	
	public TileMachineBase() {
		if(this instanceof TileBiomeMorpher) {
			this.capacity = 800000;
			this.maxReceive = 20000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileNodeGenerator) {
			this.capacity = 50000000;
			this.maxReceive = 40000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileElectricBellows) {
			this.capacity = 20000;
			this.maxReceive = 5000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileReconstructor) {
			this.capacity = 40000;
			this.maxReceive = 5000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileCondenser) {
			this.capacity = 50000000;
			this.maxReceive = 50000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileBloodFabricator) {
			this.capacity = 50000000;
			this.maxReceive = 50000;
			this.energyStorage = new EnergyStorage(capacity);
		}
		if(this instanceof TileManaFabricator) {
			this.capacity = 30000;
			this.maxReceive = 10000;
			this.energyStorage = new EnergyStorage(capacity);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		energyStorage.readFromNBT(compound);	
		if (energy < 0) {
			energy = 0;
		}
		compound.setInteger("Energy", energy);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		energyStorage.writeToNBT(compound);		
		this.energy = compound.getInteger("Energy");

		if (energy > capacity) {
			energy = capacity;
		}
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,boolean simulate) {
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyStorage.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {
		return energy;
	}

	@Override
	public int getMaxEnergyStored() {
		return capacity;
	}

}
