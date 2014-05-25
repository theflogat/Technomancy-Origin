package democretes.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import democretes.blocks.gui.GuiProcessorTC;
import democretes.blocks.gui.container.ContainerProcessorTC;
import democretes.blocks.machines.tiles.TileTCProcessor;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case 0: 
			if(world.getBlockTileEntity(x, y, z) instanceof TileTCProcessor) {
				return new ContainerProcessorTC(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
			}
			break;
	}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		switch(ID) {
			case 0: 
				if(world.getBlockTileEntity(x, y, z) instanceof TileTCProcessor) {
					return new GuiProcessorTC(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
				}
				break;
		}
		return null;
	}

}
