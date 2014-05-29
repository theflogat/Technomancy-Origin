package democretes.blocks.machines;

import java.util.List;
import java.util.Random;

import thaumcraft.common.lib.InventoryHelper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileProcessorBase;
import democretes.blocks.machines.tiles.TileTCProcessor;
import democretes.handlers.ConfigHandler;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockBMProcessor extends BlockBase {

	public BlockBMProcessor(int id) {
		super(id);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.PROCESSOR_NAME + "BM");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(player != null) {
			if(tile instanceof TileBMProcessor) {
				player.openGui(Technomancy.instance, 1, world, x, y, z);
			}
		}		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		System.out.println(tile.blockMetadata);
		if(entity instanceof EntityPlayer && tile != null) {
			EntityPlayer player = (EntityPlayer)entity;
			if(tile instanceof TileBMProcessor) {
				((TileBMProcessor)tile).owner = player.getDisplayName();
			}
		}
	}	
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs tab, List list)	  {
	    if(ConfigHandler.thaumcraft) {
		    list.add(new ItemStack(id, 1, 0));
	    }
	    if(ConfigHandler.bloodmagic) {
		    list.add(new ItemStack(id, 1, 1));
	    }
	}
	
	@SideOnly(Side.CLIENT)
	public Icon[] iconBM = new Icon[4];
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		iconBM[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + "BMSide");
		iconBM[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + "BMActive");
		iconBM[2] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + "BMInactive");
		iconBM[3] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + "BMTop");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if(meta == 0) {
			if(side == 1) {
				return iconBM[3];
			}
			if(side > 1) {
				return iconBM[2];
			}
			return iconBM[1];
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		TileEntity tile = access.getBlockTileEntity(x, y, z);
		if(tile instanceof TileTCProcessor) {
			if(side == 1) {
				return iconBM[3];
			}
			if(side > 1) {
				if(((TileProcessorBase)tile).isActive()) {
					return iconBM[1];
				}
				return iconBM[2];
			}
		}
		return iconBM[1];		
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World w, int i, int j, int k, Random r)	  {
		TileEntity te = w.getBlockTileEntity(i, j, k);
	    if ((te != null) && ((te instanceof TileTCProcessor)))	    {
	    	if (((TileTCProcessor)te).isActive()) {
	    		float f = i + 0.5F;
	    		float f1 = j + 0.2F + r.nextFloat() * 5.0F / 16.0F;
	    		float f2 = k + 0.5F;
	    		float f3 = 0.52F;
	    		float f4 = r.nextFloat() * 0.5F - 0.25F;
	      
	    		w.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	    		w.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	        
	    		w.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	    		w.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	        
	    		w.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	    		w.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	        
	    		w.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	    		w.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	    	}
	    }
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileBMProcessor();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)	  {
	    InventoryHelper.dropItems(world, x, y, z);
	    super.breakBlock(world, x, y, z, id, meta);
	}
	

}
