package democretes.renderer.tiles;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.common.blocks.BlockJar;
import thaumcraft.common.blocks.BlockTube;
import thaumcraft.common.tiles.TileTubeFilter;
import democretes.blocks.TMBlocks;
import democretes.blocks.machines.BlockTeslaCoil;
import democretes.blocks.machines.tiles.TileTeslaCoil;
import democretes.blocks.storage.TileEssentiaContainer;
import democretes.lib.BlockIds;
import democretes.lib.Reference;
import democretes.renderer.models.ModelTeslaCoil;

public class TileTeslaCoilRenderer extends TileEntitySpecialRenderer {
	
	ModelTeslaCoil model = new ModelTeslaCoil();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Reference.MODEL_TESLA_COIL_TEXTURE);

	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1.0F, -1.0F, 1.0f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glTranslatef(0.0F, 2.0F, 0.0F);
		renderFacing(entity);		
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPushMatrix();
		if(((TileTeslaCoil)entity).aspectFilter != null) {
			Color color = new Color(((TileTeslaCoil)entity).aspectFilter.getColor());
			GL11.glColor3d(color.getRed() / 255.0F * 0.2F, color.getGreen() / 255.0F * 0.2F, color.getBlue() / 255.0F * 0.2F);
		}
		model.renderRings();
		GL11.glPopMatrix();
		
		
		GL11.glPopMatrix();
	}
	
	public void renderFacing(TileEntity entity) {
		switch (((TileTeslaCoil)entity).facing){
		case 0:
			GL11.glTranslatef(0.0F, -2.0F, 0.0F);
			GL11.glRotatef(180, 0.0F, 1.0F, 0.0F); break;
		case 1:
			GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);break;
		case 3: 
			GL11.glTranslatef(0.0F, -1.0F, -1.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F); break;
		case 2:
			GL11.glTranslatef(0.0F, -1.0F, 1.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F); 
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);break;
		case 5: 
			GL11.glTranslatef(1.0F, -1.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);break;
		case 4:
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(90.0F, 1.0F, 0F, 0.0F);
		}
	}
	
	public void renderFilter(TileEntity tile)  {
		if (this.tileEntityRenderer.renderEngine == null) {
		      return;
		}	    
		TileTeslaCoil entity = (TileTeslaCoil)tile;
		    
		    
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef(0F, -1.5F, 0F);
		    
		RenderBlocks renderBlocks = new RenderBlocks();
		    
		GL11.glDisable(2896);
		    
	    Tessellator t = Tessellator.instance;
		    
		renderBlocks.setRenderBounds(0.4D, 0.1D, 0.4D, 0.5D, 0.3D, 0.5D);
		    
		t.startDrawingQuads();
		
		t.setColorOpaque_I((entity).aspectFilter.getColor());
		
		    
		Icon icon = BlockTeslaCoil.iconFilter;
		    
		this.tileEntityRenderer.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		    
		Block coil = TMBlocks.teslaCoil;

		renderBlocks.renderFaceYNeg(coil, 0.0D, 0.0D, 0.0D, icon);
		renderBlocks.renderFaceYPos(coil, 0.0D, 0.0D, 0.0D, icon);
		renderBlocks.renderFaceZNeg(coil, 0.0D, 0.0D, 0.0D, icon);
		renderBlocks.renderFaceZPos(coil, 0.0D, 0.0D, 0.0D, icon);
		renderBlocks.renderFaceXNeg(coil, 0.0D, 0.0D, 0.0D, icon);
		renderBlocks.renderFaceXPos(coil, 0.0D, 0.0D, 0.0D, icon);
		    
		t.draw();	    
		    

		GL11.glEnable(2896);	    
		GL11.glPopMatrix();
		    
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}


}
