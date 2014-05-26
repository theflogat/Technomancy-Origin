package democretes.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thermalexpansion.block.TEBlocks;
import thermalexpansion.block.ender.BlockTesseract;
import thermalexpansion.block.machine.BlockMachine;
import thermalexpansion.item.TEItems;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeManaInfusion;
import vazkii.botania.common.item.ModItems;
import WayofTime.alchemicalWizardry.api.altarRecipeRegistry.AltarRecipeRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import democretes.blocks.TMBlocks;
import democretes.items.TMItems;

public class CraftingHandler {
	
	public static void initFurnaceRecipe() {
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedIron.itemID, i, new ItemStack(Item.ingotIron, 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedGold.itemID, i, new ItemStack(Item.ingotGold, 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedCopper.itemID, i, new ItemStack(TEItems.ingotCopper.getItem(), 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedTin.itemID, i, new ItemStack(TEItems.ingotTin.getItem(), 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedSilver.itemID, i, new ItemStack(TEItems.ingotSilver.getItem(), 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedLead.itemID, i, new ItemStack(TEItems.ingotLead.getItem(), 1 + i, 0), 100);
		}
		for(int i = 0; i < 5; i++) {
			FurnaceRecipes.smelting().addSmelting(TMItems.processedNickel.itemID, i, new ItemStack(TEItems.ingotNickel.getItem(), 1 + i, 0), 100);
		}
	}
	
	public static void initThaumcraftRecipes() {
		
		//Infusion Recipes
		ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR", new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
				new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75), new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
				new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Block.blockDiamond), new ItemStack((ItemApi.getBlock("blockCosmeticSolid", 4)).itemID, 1, 4), new ItemStack((ItemApi.getBlock("blockCosmeticSolid", 4)).itemID, 1, 4), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TEBlocks.blockEnergyCell, 1, 3)}));
		ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
				new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45), new ItemStack((ItemApi.getBlock("blockMetalDevice", 7)).itemID, 1, 7),
				new ItemStack[] { new ItemStack((ItemApi.getItem("itemShard", 4)).itemID, 1, 4), new ItemStack((ItemApi.getItem("itemShard", 4)).itemID, 1, 4), new ItemStack(Item.bucketEmpty), new ItemStack(Item.bucketEmpty), new ItemStack(Item.emerald), new ItemStack(Item.emerald)}));
		ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND", new ItemStack(TMItems.itemWandCores, 1, 0), 25,
				new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 50), new ItemStack(ConfigItems.itemWandRod, 1, 1),
				new ItemStack[] { new ItemStack((TEItems.capacitorResonant).itemID, 1, 4), new ItemStack((TEItems.powerCoilElectrum).itemID, 1, 193), new ItemStack((TEItems.powerCoilSilver).itemID, 1, 194), new ItemStack((TEItems.powerCoilGold).itemID, 1, 195), new ItemStack(TMItems.itemMaterial, 1, 1) }));
		ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER", new ItemStack(TMBlocks.condenserBlock), 25,
				new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(ConfigBlocks.blockStoneDevice, 1, 2), 
				new ItemStack[] { BlockMachine.machineFrame, new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack((ItemApi.getBlock("blockCosmeticSolid", 4)).itemID, 1, 4), new ItemStack((ItemApi.getBlock("blockCosmeticSolid", 4)).itemID, 1, 4)}));
		
		//Arcane Recipes
		ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS", new ItemStack(TMBlocks.cosmeticOpaque, 4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
			new Object[] { "GG", "GG", 
			'G', new ItemStack(Block.glass)		}));
		ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS", new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
			new Object[] { "QNQ", "Q Q", "QQQ",
			'Q', new ItemStack(TMBlocks.cosmeticOpaque),
			'N', new ItemStack(TMItems.itemMaterial, 0)		}));
		ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25).add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
				new Object[] {" C ", "GIG", "IRI",
				'C', new ItemStack(TMItems.itemMaterial, 1, 1),
				'G', new ItemStack(TMItems.itemMaterial, 1, 2),
				'I', new ItemStack(TMItems.itemMaterial, 0),
				'R', new ItemStack(Item.redstone)				}));
		ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0), new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
				new Object[] {" C ", "GIG", "IWI",
				'W', new ItemStack((ItemApi.getBlock("blockJar", 0)).itemID, 1, 0),
				'C', new ItemStack(TMItems.itemMaterial, 1, 1),
				'G', new ItemStack(TMItems.itemMaterial, 1, 2),
				'I', new ItemStack((ItemApi.getBlock("blockTube", 0)).itemID, 1, 0),				}));	
		ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher), new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
				new Object[] {" E ", "TOB", "GCG",
				'E', new ItemStack(Item.emerald),
				'T', new ItemStack((ItemApi.getItem("itemResource", 11)).itemID, 1, 11),
				'O', new ItemStack((ItemApi.getBlock("blockCosmeticSolid", 0)).itemID, 1, 0),
				'B', new ItemStack((ItemApi.getBlock("blockCustomPlant", 4)).itemID, 1, 4),
				'G', new ItemStack(TMItems.itemMaterial, 1, 2),
				'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
		ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil), new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20).add(Aspect.EARTH, 20),
				new Object[] {" N ", " C ", "TBT",
				'N', new ItemStack(TMItems.itemMaterial, 1, 0),
				'C', new ItemStack(TMItems.itemMaterial, 1, 1),
				'T', new ItemStack((ItemApi.getItem("itemResource", 2)).itemID, 1, 2),
				'B', new ItemStack((ItemApi.getBlock("blockTube", 4)).itemID, 1, 4)		}));
		ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4), new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
				new Object[] {" C ", " T ", " S ",
				'C', new ItemStack(TMItems.itemMaterial, 1, 1),
				'T', new ItemStack((ItemApi.getItem("itemResource", 2)).itemID, 1, 2),
				'S', new ItemStack(Item.stick)	}));
		ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS", new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
				new Object[] {"TG ", "EBC", "TG ",
				'T', new ItemStack((ItemApi.getItem("itemResource", 2)).itemID, 1, 2),
				'G', new ItemStack(TMItems.itemMaterial, 1, 2),
				'E', new ItemStack(TEBlocks.blockEnergyCell, 1, 2),
				'B', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 0),
				'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
		//Crucible Recipes
		ResearchHandler.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0), new ItemStack((ItemApi.getItem("itemResource", 2)).itemID, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));
		
		//Crafting Recipes
		ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
			new Object[] {"  N", " T ", "N  ", 
			'N', new ItemStack(Item.redstone), 
			'T', new ItemStack((ItemApi.getItem("itemResource",2)).itemID, 1, 2)	}));
		ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
			new Object[] {" T ", "TIT", " T ",
			'T', new ItemStack(TMItems.itemMaterial, 0),
			'I', new ItemStack(Item.ingotIron)	}));
		ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
			new Object[] {" NI", "NIN", "IN ",
			Character.valueOf('N'), "nuggetIron",
			Character.valueOf('I'), "dyeBlack"		}));
		ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
			new Object[] {			" IC", "IPI", "NI ",
			'I', new ItemStack(Item.ingotIron),
			'C', new ItemStack((ItemApi.getItem("itemWandCap", 0).itemID), 1, 0),
			'P', new ItemStack(TMItems.itemMaterial, 1, 3),
			'N', new ItemStack(Item.goldNugget)		}));
		
		//Wand Recipes
		ItemStack electric = ItemApi.getItem("itemWandCasting", 72);
		((ItemWandCasting)electric.getItem()).setCap(electric, (WandCap)WandCap.caps.get("thaumium"));
		((ItemWandCasting)electric.getItem()).setRod(electric, (WandRod)WandRod.rods.get("electric"));
		ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60).add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
			new Object[]{"  C", " R ", "C  ", 
			Character.valueOf('C'), ((WandCap)WandCap.caps.get("thaumium")).getItem(), 
			Character.valueOf('R'), ((WandRod)WandRod.rods.get("electric")).getItem()		});
	}
	
	public static void initBloodMagicRecipes() {
		//Altar Recipes
		AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMBlocks.bloodDynamo), new ItemStack(TEBlocks.blockDynamo), 1, 10000, 100, 100, true);
		AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 0), new ItemStack(Item.ingotIron), 0, 1000, 100, 100, true);
		AltarRecipeRegistry.registerAltarRecipe(new ItemStack(TMItems.itemBM, 1, 1), TEItems.powerCoilGold, 0, 1000, 100, 100, true);
		
		//Normal Recipes
		GameRegistry.addShapedRecipe(new ItemStack(TMBlocks.bloodFabricator), 
			new Object[] {" T ", "IMI", "CAC",
			'T', new ItemStack(TEBlocks.blockTank, 1, 3),
			'I', new ItemStack(TMItems.itemBM, 1, 0),
			'M', BlockMachine.machineFrame,
			'C', new ItemStack(TMItems.itemBM, 1, 1),
			'A', new ItemStack(BlockTesseract.tesseractFrameFull.getItem())		});
	}
	
	public static RecipeManaInfusion manaCoilRecipe;
	
	public static void initBotaniaRecipes() {
		//ManaInfusion
		manaCoilRecipe = BotaniaAPI.registerManaInfusionRecipe(new ItemStack(TMItems.itemBO, 1, 0), TEItems.powerCoilSilver, 3000);
		
		//Normal Recipes
		oreDictRecipe(new ItemStack(TMItems.itemBO, 1, 1),
			new Object[] {" M ", "MIM", " M ",
			'M', new ItemStack(ModItems.manaResource, 1, 0),
			'I', "ingotIron"		});
		GameRegistry.addRecipe(new ItemStack(TMBlocks.flowerDynamo), 
			new Object[] {" C ", "GIG", "IWI",
			'W', new ItemStack(Item.redstone),
			'C', new ItemStack(TMItems.itemBO, 1, 0),
			'G', new ItemStack(TMItems.itemBO, 1, 1),
			'I', new ItemStack(ModItems.manaResource, 1, 0)				});
		GameRegistry.addRecipe(new ItemStack(TMBlocks.manaFabricator), 
				new Object[] {"CDC", "IDI", " P ",
				'C', new ItemStack(TMItems.itemBO, 1, 1),
				'I', new ItemStack(ModItems.manaResource, 1, 0),
				'D', new ItemStack(ModItems.manaResource, 1, 2),
				'P', new ItemStack(Item.flowerPot)				});
	}
	
	static IRecipe oreDictRecipe(ItemStack res, Object[] params) {
		IRecipe rec = new ShapedOreRecipe(res, params);
		CraftingManager.getInstance().getRecipeList().add(rec);
		return rec;
	}
	
}
