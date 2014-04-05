package democretes.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.wands.IWandRodOnUpdate;
import thaumcraft.common.items.wands.ItemWandCasting;
import thermalexpansion.item.tool.ItemCapacitor;
import thermalexpansion.item.tool.ItemCapacitor.Types;

public class ElectricWandUpdate implements IWandRodOnUpdate{

	int slot = 0;
	int energy = 0;
	
	@Override
	public void onUpdate(ItemStack itemstack, EntityPlayer player) {
		if(itemstack != null) {
			if(itemstack.getItem() instanceof ItemWandCasting) {
				if(((ItemWandCasting)itemstack.getItem()).getAspectsWithRoom(itemstack) != null) {
					if(checkInventory(player)) {
						ItemCapacitor capacitor = (ItemCapacitor)player.inventory.mainInventory[slot].getItem();
						Aspect[] al = Aspect.getPrimalAspects().toArray(new Aspect[0]);						
						for(int i = 0; i < al.length; i++) {
							while(((ItemWandCasting)itemstack.getItem()).getVis(itemstack, al[i]) < ((ItemWandCasting)itemstack.getItem()).getMaxVis(itemstack)){
								if(checkInventory(player)) {
									if(capacitor.extractEnergy(player.inventory.mainInventory[slot], 10000, true) != 0) {
										this.energy += capacitor.extractEnergy(player.inventory.mainInventory[slot], 10000, false);
									}else{
										return;
									}
								}else{
									return;
								}														
								if(this.energy >= 10000) {
										((ItemWandCasting)itemstack.getItem()).addVis(itemstack, al[i], 1, true);
										this.energy -= 10000;
								}else{
									return;
								}
							}
						}
					}
				}
			}
		}
	}
	
	boolean checkInventory(EntityPlayer player) {
		ItemStack[] inv = player.inventory.mainInventory;
		for(int i = 0; i < inv.length; i++) {
			if(inv[i] != null) {
				if(inv[i].getItem() instanceof ItemCapacitor) {
					if(((ItemCapacitor)inv[i].getItem()).getDamage(inv[i]) == Types.CREATIVE.ordinal()) {
						((ItemCapacitor)inv[i].getItem()).setActiveState(inv[i], true);
					}
					if(((ItemCapacitor)inv[i].getItem()).isActive(inv[i])) {
						this.slot = i;
						return true;
					}
				}
			}
		}
		return false;
	}


}
