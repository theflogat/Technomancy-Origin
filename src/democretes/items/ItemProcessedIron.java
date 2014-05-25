package democretes.items;

import java.util.List;

import cofh.util.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class ItemProcessedIron extends ItemBase {	

	String[] processors = {"Thaumcraft", "Botania", "Blood Magic", "Ars Magica", "Witchery" };

	public ItemProcessedIron(int id) {
		super(id);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}
	
	public Icon[] itemIcon = new Icon[5];
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore0");
		itemIcon[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore1");
		itemIcon[2] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore2");
		itemIcon[3] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore3");
		itemIcon[4] = icon.registerIcon(Reference.TEXTURE_PREFIX + "ore4");
	}
	
	@Override	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par) {
		return itemIcon[par];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int par2)	  {
		return 16777215;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Reference.MOD_PREFIX + LibNames.ITEM_PROCESSED_IRON_NAME;
	}
	
	@Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
    	if(!StringHelper.isShiftKeyDown()) {
    		list.add(StringHelper.getActivationText("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
    		list.add(StringHelper.getFlavorText("info.techno:shift"));
    	}else{
    		list.add(StringHelper.getActivationText("info.techno:purity") + ": " + (stack.getItemDamage() + 1));
    		list.add(StringHelper.localize("info.techno:process") + ":");
    		for(int i = 0; i < processors.length; i++) {
    			if(stack.stackTagCompound.getBoolean(processors[i])) {
    				list.add(processors[i]);
    			}
    		}
    	}
    }
    


}
