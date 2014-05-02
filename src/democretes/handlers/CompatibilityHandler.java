package democretes.handlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.config.ConfigItems;
import cpw.mods.fml.common.Loader;

public class CompatibilityHandler {
		
	public static void checkThisShit() {
		if(ConfigHandler.compatiblity) {
			if(Loader.isModLoaded("AWWayofTime")) {
				ConfigHandler.bloodmagic = true;
				System.out.println("Nazi super science activated");
			}
			if(Loader.isModLoaded("Botania")) {
				ConfigHandler.botania = true;
				System.out.println("Hippy scientists encountered");
			}
			if(Loader.isModLoaded("Thaumcraft")) {
				ConfigHandler.thaumcraft = true;
				System.out.println("Alkahestry has been discovered");
			}
			ConfigHandler.compatiblity = false;
		}
	}
	
	public static void smeltify() {
		if(ConfigHandler.bonus) {
			ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(Item.goldNugget, 9, 0));
		    ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(ConfigItems.itemNugget, 9, 0));
		    ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(ConfigItems.itemNugget, 9, 5));
		    ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(ConfigItems.itemNugget, 9, 1));
		    ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(ConfigItems.itemNugget, 9, 2));
		    ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(ConfigItems.itemNugget, 9, 3));
		    ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(ConfigItems.itemNugget, 9, 4));
		}
	}

}
