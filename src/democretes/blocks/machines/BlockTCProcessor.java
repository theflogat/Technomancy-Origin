package democretes.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileTCProcessor;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class BlockTCProcessor extends BlockBase {

	public BlockTCProcessor(int id) {
		super(id);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.TC_PROCESSOR_NAME);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileTCProcessor();
	}

}
