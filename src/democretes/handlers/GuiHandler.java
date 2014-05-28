package democretes.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import democretes.blocks.gui.GuiProcessorBM;
import democretes.blocks.gui.GuiProcessorTC;
import democretes.blocks.gui.container.ContainerBMProcessor;
import democretes.blocks.gui.container.ContainerTCProcessor;
import democretes.blocks.machines.tiles.TileBMProcessor;
import democretes.blocks.machines.tiles.TileTCProcessor;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
			case 0: 
				return new ContainerTCProcessor(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
			case 1:
				return new ContainerBMProcessor(player.inventory, ((TileBMProcessor)world.getBlockTileEntity(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		switch(ID) {
			case 0: 
				return new GuiProcessorTC(player.inventory, ((TileTCProcessor)world.getBlockTileEntity(x, y, z)));
			case 1:
				return new GuiProcessorBM(player.inventory, ((TileBMProcessor)world.getBlockTileEntity(x, y, z)));
		}
		return null;
	}

}
