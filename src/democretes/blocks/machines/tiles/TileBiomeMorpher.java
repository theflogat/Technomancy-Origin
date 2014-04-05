package democretes.blocks.machines.tiles;

import java.util.Random;

import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.lib.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;


public class TileBiomeMorpher extends TileMachineBase implements INode{	
	
	private AspectList aspects = new AspectList();
	private int amount = 35;
	
	@Override
	public void updateEntity() {
		if(!this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
			if (this.getEnergyStored() > 20000) {
				alterBiome();
				alterBiome();
				this.energyStorage.extractEnergy(20000, false);
			}
		}
	}		
	
	void alterBiome() {
		if (this.worldObj.isRemote) {
			return;
		}
		Random rand = new Random();
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		BiomeGenBase bm = biomeForMeta(meta);
		int xx = this.xCoord + rand.nextInt(100) - rand.nextInt(100);
		int zz = this.zCoord + rand.nextInt(100) - rand.nextInt(100);
		BiomeGenBase bg = this.worldObj.getBiomeGenForCoords(xx, zz);
		if (bg.biomeID != bm.biomeID) {
			Utils.setBiomeAt(this.worldObj, xx, zz, bm);			
		}
		worldObj.markBlockRangeForRenderUpdate(xx - 1, xx + 1, 1, 256, zz - 1, zz + 1);
		
	}
	
	BiomeGenBase checkBiome() {
		if (!this.worldObj.isRemote) {
			BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(this.xCoord, this.yCoord);
			if (biome.biomeID == ThaumcraftWorldGenerator.biomeTaint.biomeID || biome.biomeID == ThaumcraftWorldGenerator.biomeEerie.biomeID || biome.biomeID == ThaumcraftWorldGenerator.biomeMagicalForest.biomeID) {
				return biome;
			}			
		}
		return null;
	}
	
	BiomeGenBase biomeForMeta(int meta) {
		BiomeGenBase biome = null;
		if (meta == 0) {
			biome = ThaumcraftWorldGenerator.biomeMagicalForest;
		}else if (meta == 1) {
			biome = ThaumcraftWorldGenerator.biomeEerie;
		}else if (meta == 2) {
			biome = ThaumcraftWorldGenerator.biomeTaint;
		}
		return biome;
	}

	@Override
	public AspectList getAspects() {		
		return this.aspects;
	}

	@Override
	public void setAspects(AspectList aspects) {
		this.aspects = aspects.copy();
	}

	@Override
	public boolean doesContainerAccept(Aspect tag) {
		return false;
	}

	@Override
	public int addToContainer(Aspect tag, int amount) {
		return 0;
	}

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) {
		return false;
	}

	@Override
	public boolean takeFromContainer(AspectList ot) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {
		if (this.aspects.getAmount(tag) >= amount) {
			return true;
		}
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList ot) {
		for (Aspect tt : ot.getAspects()) {
			if (this.aspects.getAmount(tt) < ot.getAmount(tt)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int containerContains(Aspect tag) {
		return this.aspects.getAmount(tag);
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public NodeType getNodeType() {
		switch (this.blockMetadata) {
		case 0:
			return NodeType.PURE;
		case 1:
			return NodeType.DARK;
		case 2:
			return NodeType.TAINTED;
		}
		return null;
	}

	@Override
	public void setNodeType(NodeType nodeType) {	
	}

	@Override
	public void setNodeModifier(NodeModifier nodeModifier) {
	}

	@Override
	public NodeModifier getNodeModifier() {
		return null;
	}

	@Override
	public int getNodeVisBase() {
		return 35;
	}

	@Override
	public void setNodeVisBase(short nodeVisBase) {
	}
	
	
	

}
