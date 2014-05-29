package democretes.blocks.machines;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thaumcraft.common.lib.InventoryHelper;
import vazkii.botania.api.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileBOProcessor;
import democretes.blocks.machines.tiles.TileProcessorBase;
import democretes.blocks.machines.tiles.TileTCProcessor;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockProcessor extends BlockBase {

	String name;
	
	public BlockProcessor(int id) {
		super(id);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(player != null) {
			if(tile instanceof TileTCProcessor) {		
				player.openGui(Technomancy.instance, 0, world, x, y, z);
			}
			if(tile instanceof TileBMProcessor) {
				player.openGui(Technomancy.instance, 1, world, x, y, z);
			}
			if(tile instanceof TileBOProcessor) {
				player.openGui(Technomancy.instance, 2, world, x, y, z);
			}
		}		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			if(tile instanceof TileBMProcessor && entity instanceof EntityPlayer) {
				((TileBMProcessor)tile).owner = ((EntityPlayer)entity).getDisplayName();
			}
		}
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Reference.MOD_PREFIX + LibNames.PROCESSOR_NAME + name;
		
	}
	
	@SideOnly(Side.CLIENT)
	public Icon[] icons = new Icon[4];
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		icons[0] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + name + "Side");
		icons[1] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + name + "Active");
		icons[2] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + name + "Inactive");
		icons[3] = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.PROCESSOR_NAME + name + "Top");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			return icons[2];
		}
		return icons[1];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		TileEntity tile = access.getBlockTileEntity(x, y, z);
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			if(((TileProcessorBase)tile).isActive()) {
				return icons[1];
			}
			return icons[2];
		}		
		return icons[1];		
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World w, int i, int j, int k, Random r)	  {
		TileEntity te = w.getBlockTileEntity(i, j, k);
	    if ((te != null) && ((te instanceof TileTCProcessor || te instanceof TileBMProcessor)))	    {
	    	if (((TileProcessorBase)te).isActive()) {
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
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		if(world.getBlockTileEntity(x, y, z) instanceof TileProcessorBase) {
			if(((TileProcessorBase)world.getBlockTileEntity(x, y, z)).isActive()) {
				return 12;
			}
		}
		return super.getLightValue(world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta)	  {
	    InventoryHelper.dropItems(world, x, y, z);
	    super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	public static class BlockTCProcessor extends BlockProcessor {

		public BlockTCProcessor(int id) {
			super(id);
			this.name = "TC";
		}
		
		@Override
		public TileEntity createNewTileEntity(World world) {
			return new TileTCProcessor();
		}
		
	}
	
	public static class BlockBMProcessor extends BlockProcessor {
		
		public BlockBMProcessor(int id) {
			super(id);
			this.name = "BM";
		}
		
		@Override
		public TileEntity createNewTileEntity(World world) {
			return new TileBMProcessor();
		}
	}
	
	public static class BlockBOProcessor extends BlockProcessor implements IWandHUD {
		
		public BlockBOProcessor(int id) {
			super(id);
			this.name = "BO";
		}
		
		@Override
		public TileEntity createNewTileEntity(World world) {
			return new TileBOProcessor();
		}

		@Override
		public void renderHUD(Minecraft minecraft, ScaledResolution res, World world, int x, int y, int z) {
			if(world.getBlockTileEntity(x, y, z) instanceof TileBOProcessor) {
				((TileBOProcessor)world.getBlockTileEntity(x, y, z)).renderHUD(minecraft, res);
			}
		}
	}

	

}
