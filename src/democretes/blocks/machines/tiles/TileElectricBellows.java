package democretes.blocks.machines.tiles;

import cofh.api.energy.EnergyStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileAlchemyFurnace;
import thaumcraft.common.tiles.TileArcaneFurnace;


public class TileElectricBellows extends TileMachineBase {

	public byte facing = 2;	
	public float inflation = 1.0F;
	boolean firstrun = false;;
	boolean direction = false;
	
	public TileElectricBellows() {
		this.capacity = 20000;
		this.maxReceive = 5000;
		this.energyStorage = new EnergyStorage(capacity);
	}
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setByte("Facing", this.facing);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.facing = compound.getByte("Facing");
	}
	
	@Override
	public void updateEntity() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.facing);
		TileEntity furnace = this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
		if(furnace instanceof TileAlchemyFurnace) {
			furnace = (TileAlchemyFurnace)this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
		}else if(this.worldObj.getBlockTileEntity(this.xCoord + (dir.offsetX *2), this.yCoord, this.zCoord + (dir.offsetZ *2)) instanceof TileArcaneFurnace) {
			furnace = (TileArcaneFurnace)this.worldObj.getBlockTileEntity(this.xCoord + (dir.offsetX *2), this.yCoord, this.zCoord + (dir.offsetZ *2));
		}			
		if(furnace != null) {
			if(furnace instanceof TileAlchemyFurnace) {
				if(this.energyStorage.extractEnergy(3000, true) == 3000) {
					if(((ISidedInventory)furnace).getStackInSlot(0) == null || ((ISidedInventory)furnace).getStackInSlot(1) != null) {
						return;
					}
					AspectList al = ThaumcraftCraftingManager.getObjectTags(((ISidedInventory)furnace).getStackInSlot(0));
					al = ThaumcraftCraftingManager.getBonusTags(((ISidedInventory)furnace).getStackInSlot(0), al);
					if(al == null || al.size() == 0) {
						return;
					}
					if(((TileAlchemyFurnace)furnace).furnaceBurnTime <= 2 && ((TileAlchemyFurnace)furnace).aspects.visSize() + al.visSize() < 50) {
						((TileAlchemyFurnace)furnace).furnaceBurnTime = 80;
						this.energyStorage.extractEnergy(3000, false);
					}					
				}
			}
			if(furnace instanceof TileArcaneFurnace) {
				if(this.energyStorage.extractEnergy(500, true) == 500) {
					System.out.println(((TileArcaneFurnace)furnace).furnaceCookTime);
					System.out.println(((TileArcaneFurnace)furnace).furnaceMaxCookTime);
					if(((TileArcaneFurnace)furnace).furnaceCookTime > 6) {
						((TileArcaneFurnace)furnace).furnaceCookTime -= 6;
						this.energyStorage.extractEnergy(500, false);
					}
				}
			}
		}
		
		
		if (this.firstrun) {
			this.inflation = (0.35F + this.worldObj.rand.nextFloat() * 0.55F);
	    }
	    this.firstrun = false;
	    if ((this.inflation > 0.35F) && (!this.direction)) {
	        this.inflation -= 0.075F;
	    }
	    if ((this.inflation <= 0.35F) && (!this.direction)) {
	        this.direction = true;
	    }
	    if ((this.inflation < 1.0F) && (this.direction)) {
	        this.inflation += 0.025F;
	    }
	    if ((this.inflation >= 1.0F) && (this.direction)) {
	        this.direction = false;
	        this.worldObj.playSound(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "mob.ghast.fireball", 0.01F, 0.5F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F, false);
	    }		
	}
	
}
