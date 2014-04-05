package democretes.renderer.tiles;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.tile.TileNodeRenderer;
import thaumcraft.common.tiles.TileJar;
import thaumcraft.common.tiles.TileNode;
import democretes.blocks.machines.tiles.TileBiomeMorpher;
import democretes.lib.Reference;
import democretes.renderer.models.ModelBiomeMorpher;

public class TileBiomeMorpherRenderer extends TileEntitySpecialRenderer{
	
	ModelBiomeMorpher model = new ModelBiomeMorpher();
	
	private static final ResourceLocation modelTexture = new ResourceLocation(Reference.MODEL_BIOME_MODIFIER_TEXTURE);
	private TileNodeRenderer nRender = new TileNodeRenderer();

	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f) {
		//renderNode(entity, x, y, z, f);
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-1F, -1F, 1f);
		GL11.glTranslatef(-.5F, -1.5F, .5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		bindTexture(modelTexture);
		model.render();
		
		GL11.glPopMatrix();
	}
	
	void renderNode(TileEntity entity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, 0.0F);
		this.nRender.renderTileEntityAt(entity, x, y, z, f);
		GL11.glPopMatrix();
	}

}
