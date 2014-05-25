package democretes.blocks.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import democretes.Technomancy;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileTCProcessor;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockTCProcessor extends BlockBase {

	public BlockTCProcessor(int id) {
		super(id);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.PROCESSOR_TC_NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileTCProcessor();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(player != null && tile instanceof TileTCProcessor) {
			player.openGui(Technomancy.instance, 0, world, x, y, z);
		}
		return false;
	}
	
	

}
