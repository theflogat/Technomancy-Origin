package democretes.blocks.machines.tiles;

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

	public boolean alchemy = false;
	public boolean infernal = false;
	public byte facing = 2;	
	public float inflation = 1.0F;
	boolean firstrun = false;;
	boolean direction = false;
	
	@Override
	public void writeCustomNBT(NBTTagCompound compound) {
		compound.setByte("Facing", this.facing);
		compound.setBoolean("Alchemy", this.alchemy);
		compound.setBoolean("Infernal", this.infernal);
	}
	
	@Override
	public void readCustomNBT(NBTTagCompound compound) {
		this.facing = compound.getByte("Facing");
		this.alchemy = compound.getBoolean("Alchemy");
		this.infernal = compound.getBoolean("Infernal");
	}
	
	@Override
	public void updateEntity() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.facing);
		TileEntity furnace = new TileEntity();
		if(alchemy) {
			furnace = (TileAlchemyFurnace)this.worldObj.getBlockTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
		}
		if(infernal) {
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
