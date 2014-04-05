package democretes.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ItemApi;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileTeslaCoil;
import democretes.lib.LibNames;
import democretes.lib.Reference;
import democretes.lib.RenderIds;

public class BlockTeslaCoil extends BlockBase {

	public BlockTeslaCoil(int id) {
		super(id);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.TESLA_COIL_NAME);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileTeslaCoil();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		ItemStack stack = player.getHeldItem();
		if(tile != null && stack == null && player.isSneaking()) {
			if(tile instanceof TileTeslaCoil) {
				if (((TileTeslaCoil)tile).aspectFilter != null ) {
					((TileTeslaCoil)tile).aspectFilter = null;
					if (world.isRemote) {
						world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}else{
						ForgeDirection fd = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[((TileTeslaCoil)tile).facing]);
						if (!player.inventory.addItemStackToInventory(new ItemStack(ConfigItems.itemResource.itemID, 1, 13))) {	
							world.spawnEntityInWorld(new EntityItem(world, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ / 3.0F, new ItemStack(ItemApi.getItem("itemResource", 13).itemID, 1, 13)));
						}
					}
				}
			}
		}
		if(stack != null) {
			if (stack.getItemDamage() == 13 && stack.itemID == ConfigItems.itemResource.itemID && stack.getItem() instanceof IEssentiaContainerItem) {
				if (((IEssentiaContainerItem)stack.getItem()).getAspects(stack) != null && ((TileTeslaCoil)tile).aspectFilter == null) {
					((TileTeslaCoil)tile).aspectFilter = ((IEssentiaContainerItem)stack.getItem()).getAspects(stack).getAspects()[0];
					System.out.println(((TileTeslaCoil)tile).aspectFilter);
					System.out.println(((IEssentiaContainerItem)stack.getItem()).getAspects(stack).getAspects()[0]);
					stack.stackSize -= 1;
					world.markBlockForUpdate(x, y, z);
					if(world.isRemote) {
						world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}						
				}				
			}
		}		
		return false;
	}
	
	private int facing;
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileTeslaCoil) {
			((TileTeslaCoil)tile).facing = this.facing;
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta){
		this.facing = ForgeDirection.OPPOSITES[side];
		return side + meta;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileTeslaCoil) {
			switch(((TileTeslaCoil)tile).facing) {
			case 0:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);break;
			case 1:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);break;
			case 2:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);break;
			case 3:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);break;
			case 4:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);break;
			case 5:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idTeslaCoil;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon iconCoil;
	public static Icon iconFilter;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconCoil = icon.registerIcon("technom:models/essentiaCoil");
		this.iconFilter = icon.registerIcon("thaumcraft:misc/coil");
	}

}
