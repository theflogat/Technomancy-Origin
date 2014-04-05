package democretes.blocks.machines;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileMannequin;

public class BlockMannequin extends BlockBase {
	

	public BlockMannequin(int id) {
		super(id);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
		}else{			
			return;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileMannequin();
	}

}
