package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import thaumcraft.api.IScribeTools;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class ItemPen extends ItemBase implements IScribeTools {

	public ItemPen(int id) {
		super(id);
		this.maxStackSize = 1;
		this.canRepair = true;
		setMaxDamage(3000);
	}
	
	
	public Icon penIcon;
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister icon) {
        this.penIcon = icon.registerIcon(Reference.TEXTURE_PREFIX + "penItem");
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1) {
    return this.penIcon;
    }
    
	public String getUnlocalizedName(ItemStack stack) {
		return Reference.MOD_PREFIX + LibNames.ITEM_PEN_NAME;
	}
    
	public void addInformation(ItemStack itemstack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("It's the 21st century,");
		info.add("why are we using quills and ink?");
	}

}
