package democretes.blocks.gui;

import democretes.blocks.gui.container.ContainerProcessorTC;
import democretes.blocks.machines.tiles.TileTCProcessor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiProcessorTC extends GuiContainer {

	public GuiProcessorTC(InventoryPlayer inventory, TileTCProcessor tileTCProcessor) {
		super(new ContainerProcessorTC(inventory, tileTCProcessor));
		
		xSize = 175;
		ySize = 167;
	}

	private static final ResourceLocation texture = new ResourceLocation("technom", "textures/gui/processorTC.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
