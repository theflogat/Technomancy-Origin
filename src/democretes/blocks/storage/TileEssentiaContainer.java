package democretes.blocks.storage;

import democretes.blocks.TileTechnomancy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileJarFillable;

public class TileEssentiaContainer extends TileJarFillable implements IAspectSource, IEssentiaTransport{

	public int max = 640;
	
	public TileEssentiaContainer() {
		this.maxAmount = this.max;
	}
	
	@Override
	public int getMinimumSuction() {
	  return this.aspectFilter != null ? 56 + (amount/100) : 48 + (amount/100);
	}

	@Override
	public int getSuctionAmount(ForgeDirection loc) {
		if (this.amount < this.maxAmount){
			if (this.aspectFilter != null) {
				return 56 + (amount/100);
			}
			return 48 + (amount/100);
		}
		return 0;
	}

	
}