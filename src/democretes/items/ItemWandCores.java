package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumcraft.api.ItemApi;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCasting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class ItemWandCores extends ItemBase{
	
	public ItemWandCores(int id) {
		super(id);
		this.setHasSubtypes(true);
	}
	
	public Icon[] coresIcon = new Icon[1];
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		coresIcon[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + "electricWandCore");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int icon) {
		return this.coresIcon[icon];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return Reference.MOD_PREFIX + LibNames.ITEM_WAND_CORES_NAME + "." + stack.getItemDamage();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < coresIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
		ItemStack electric = ItemApi.getItem("itemWandCasting", 72);
		((ItemWandCasting)electric.getItem()).setCap(electric, (WandCap)WandCap.caps.get("thaumium"));
		((ItemWandCasting)electric.getItem()).setRod(electric, (WandRod)WandRod.rods.get("electric"));
		list.add(electric);
	}



}
