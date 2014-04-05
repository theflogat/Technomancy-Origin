package democretes.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockCrystal extends BlockBase {

	public BlockCrystal(int id) {
		super(id);
		setBlockUnbreakable();
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.CRYSTAL_NAME);
	}
	
	@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		if(player != null) {
			int meta = world.getBlockMetadata(x, y, z);
			if(meta == 0) {
				Random rand = new Random();
				int dim = rand.nextInt(DimensionManager.getIDs().length);
				if(DimensionManager.isDimensionRegistered(DimensionManager.getIDs()[dim])) {
					player.travelToDimension(DimensionManager.getIDs()[dim]);
				}
			}					
		}
	}
	
	public Icon crystalIcon;	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		
	}	
	
	@Override
	public void getSubBlocks(int id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
		list.add(new ItemStack(id, 1, 2));
	}


	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
