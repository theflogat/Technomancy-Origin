package democretes.renderer.tiles;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cofh.render.RenderHelper;
import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.common.block.LifeEssenceBlock;
import thaumcraft.common.blocks.BlockJar;
import democretes.blocks.TMBlocks;
import democretes.blocks.machines.tiles.TileBloodFabricator;
import democretes.blocks.storage.TileEssentiaContainer;
import democretes.lib.Reference;
import democretes.renderer.models.ModelBloodFabricator;

public class TileBloodFabricatorRenderer extends TileEntitySpecialRenderer {
	
	ModelBloodFabricator model = new ModelBloodFabricator();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Reference.MODEL_BLOOD_FABRICATOR_TEXTURE);

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float t) {
		if (entity instanceof TileBloodFabricator) {			
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			GL11.glScalef(-1F, -1F, 1f);
			GL11.glTranslatef(-.5F, -1.5F, .5F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			bindTexture(modelTexture);
			renderLiquid(entity, x, y, z, t);
			model.render();
		
			GL11.glPopMatrix();
		}
	}
	
	public void renderLiquid(TileEntity tile, double x, double y, double z, float f)  {
	    if (this.tileEntityRenderer.renderEngine == null) {
	      return;
	    }	    
	    TileBloodFabricator fabricator = (TileBloodFabricator)tile;
	    
	    if(fabricator.tank.getFluidAmount() <= 0) {
	    	return;
	    }
	    
	    GL11.glPushMatrix();
	    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	    GL11.glTranslatef(0F, -1.5F, 0F);
	    
	    RenderBlocks renderBlocks = new RenderBlocks();
	    
	    GL11.glDisable(2896);
	    
	    float level = fabricator.tank.getFluidAmount()/fabricator.tank.getCapacity();
	    
	    Tessellator t = Tessellator.instance;
	    
	    renderBlocks.setRenderBounds(0.25D, 0.25D, 0.25D, 0.75D, 0.25D + level, 0.75D);
	    
	    t.startDrawingQuads();
	    	    
	    Icon icon = RenderHelper.getFluidTexture(AlchemicalWizardry.lifeEssenceFluid);
	    
	    this.tileEntityRenderer.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
	    
	    Block tank = TMBlocks.bloodFabricator;

	    renderBlocks.renderFaceYNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceYPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceZNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceZPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceXNeg(tank, -0.5D, 0.0D, -0.5D, icon);
	    renderBlocks.renderFaceXPos(tank, -0.5D, 0.0D, -0.5D, icon);
	    
	    t.draw();	    
	    

	    GL11.glEnable(2896);	    
	    GL11.glPopMatrix();
	    
	    GL11.glColor3f(1.0F, 1.0F, 1.0F);
	  }
}
