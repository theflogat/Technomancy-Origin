package democretes.blocks;

import democretes.Technomancy;
import democretes.lib.CreativeTabTM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends BlockContainer {
	
	public BlockBase(int id) {
		super(id, Material.iron);
		setCreativeTab(Technomancy.tabsTM);
		setHardness(2F);
	}	

}
