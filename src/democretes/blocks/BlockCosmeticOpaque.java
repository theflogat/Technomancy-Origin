package democretes.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.Technomancy;
import democretes.lib.LibNames;
import democretes.lib.Reference;
import democretes.lib.RenderIds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockCosmeticOpaque extends Block {

	public BlockCosmeticOpaque(int id) {
		super(id, Material.glass);
		setStepSound(Block.soundGlassFootstep);
		setHardness(.25F);
		setUnlocalizedName(Reference.MOD_PREFIX + LibNames.COSMETIC_OPAQUE_NAME);
		this.setCreativeTab(Technomancy.tabsTM);
	}
	
	@SideOnly(Side.CLIENT)
	private Icon glassIcon;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		glassIcon = icon.registerIcon(Reference.TEXTURE_PREFIX + LibNames.COSMETIC_OPAQUE_NAME);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int side, int meta) {
		return glassIcon;
		
	}
	
	public int quantityDropped(Random par1Random) {
	       return 1;
	   }
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
		return 1;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	public boolean renderAsNormalBlock() {
		return false;
	}

}
