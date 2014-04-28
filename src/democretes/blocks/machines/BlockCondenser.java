package democretes.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cofh.render.IconRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileCondenser;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockCondenser extends BlockBase {

	public BlockCondenser(int id) {
		super(id);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.CONDENSER_NAME);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)    {
        int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }
	
	@SideOnly(Side.CLIENT)
	public Icon machineFace;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		machineFace = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.CONDENSER_NAME);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		if(side == 0) {
			return IconRegistry.getIcon("MachineBottom");
		}
		if(side == 1) {
			return IconRegistry.getIcon("MachineTop");
		}
		if((meta == 0 && side == 3) || (meta == 1 && side == 4) || (meta == 2 && side == 2) || (meta == 3 && side == 5)) {
			return machineFace;
		}
		return IconRegistry.getIcon("MachineSide");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileCondenser();
	}
	
	

}
