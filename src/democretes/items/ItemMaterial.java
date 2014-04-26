package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.common.tiles.TileMirrorEssentia;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.machines.tiles.TileTeslaCoil;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class ItemMaterial extends ItemBase {

	public ItemMaterial(int id) {
		super(id);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}

	
	public Icon[] itemIcon = new Icon[6];
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + "neutronizedMetal");
		itemIcon[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + "enchantedCoil");
		itemIcon[2] = icon.registerIcon(Reference.TEXTURE_PREFIX + "neutronizedGear");
		itemIcon[3] = icon.registerIcon(Reference.TEXTURE_PREFIX + "penCore");
		itemIcon[4] = icon.registerIcon(Reference.TEXTURE_PREFIX + "coilCoupler");
		itemIcon[5] = icon.registerIcon(Reference.TEXTURE_PREFIX + "bloodIngot");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par) {
		return this.itemIcon[par];
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return Reference.MOD_PREFIX + LibNames.ITEM_MATERIAL_NAME + "." + stack.getItemDamage();
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < itemIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
	}
	
	private TileTeslaCoil entity;
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null && stack.getItemDamage() == 4) {			
				if(tile instanceof IAspectSource && tile instanceof TileMirrorEssentia == false) {
					if(this.entity != null) {
						if(entity.link && entity.worldObj == tile.worldObj && world.getBlockTileEntity(x, y, z) != entity.worldObj.getBlockTileEntity(entity.xCoord, entity.yCoord, entity.zCoord)) {
							entity.sources.add(new ChunkCoordinates(x, y, z));
							player.sendChatToPlayer(ChatMessageComponent.createFromText("Linked"));
							entity.link = false;
							return true;
						}						
					}
				}else{
					player.sendChatToPlayer(ChatMessageComponent.createFromText("Not a valid source"));
					entity.link = false;
				}
				if(tile instanceof TileTeslaCoil) {				
					TileTeslaCoil coil = (TileTeslaCoil)tile;
					this.entity = coil;
					if(player.isSneaking() && !entity.link) {
						entity.sources.clear();
						player.sendChatToPlayer(ChatMessageComponent.createFromText("Links Cleared"));
						return false;
					}else if(!entity.link) {
						player.sendChatToPlayer(ChatMessageComponent.createFromText("Begin Linking"));					
						entity.link = true;
						return true;
					}
				}
			}
		}
		return false;
	}


}
