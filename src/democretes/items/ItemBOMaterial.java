package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class ItemBOMaterial extends ItemBase {

	public ItemBOMaterial(int id) {
		super(id);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	
	public Icon[] itemIcon = new Icon[2];
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + "manaCoil");
		itemIcon[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + "manaGear");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par) {
		return this.itemIcon[par];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return Reference.MOD_PREFIX + LibNames.ITEM_BO_MATERIAL_NAME + "." + stack.getItemDamage();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < itemIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
	}
}
