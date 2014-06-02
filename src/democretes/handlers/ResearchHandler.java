package democretes.handlers;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import democretes.blocks.TMBlocks;
import democretes.items.TMItems;

public class ResearchHandler {
	
	public static HashMap<String, Object> recipes = new HashMap();
	
	public static void init() {
		
		ResearchCategories.registerCategory("TECHNOMANCY", new ResourceLocation("technom", "textures/misc/technomancyCategory.png"), new ResourceLocation("thaumcraft", "textures/gui/gui_researchback.png"));
	
		initThaumcraft();
	
	}
	
	private static void initThaumcraft() {
		
		new ResearchItem("TECHNOBASICS", "TECHNOMANCY", new AspectList(), 0, 0, 0, new ResourceLocation("technom", "textures/misc/technomancyBasics.png")).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.TECHNOBASICS.1"), new ResearchPage((CrucibleRecipe)recipes.get("NeutronizedMetal")), new ResearchPage((IRecipe)recipes.get("MagicCoil")), new ResearchPage((IRecipe)recipes.get("NeutronizedGear"))}).setAutoUnlock().setRound().setStub().registerResearchItem();
		
		new ResearchItem("QUANTUMJARS", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5).add(Aspect.VOID, 5).add(Aspect.MAGIC, 5), 1, -2, 0, new ItemStack(TMBlocks.essentiaContainer)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.QUANTUMJARS.1"),  new ResearchPage((IArcaneRecipe)recipes.get("QuantumJar")), new ResearchPage((IArcaneRecipe)recipes.get("QuantumGlass")) }).setParents(new String[] { "TECHNOBASICS" }).setSecondary().registerResearchItem();
		
		new ResearchItem("DYNAMO", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.MAGIC, 5), 2, 2, 3, new ItemStack(TMBlocks.nodeDynamo)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.DYNAMO.1"),  new ResearchPage((IArcaneRecipe)recipes.get("EssentiaDynamo")), new ResearchPage("techno.research_page.DYNAMO.2"), new ResearchPage((IArcaneRecipe)recipes.get("NodeDynamo")) }).setParents(new String[] { "TECHNOBASICS" }).setSecondary().registerResearchItem();
		
		new ResearchItem("NODEGENERATOR", "TECHNOMANCY", new AspectList().add(Aspect.MECHANISM, 5).add(Aspect.ENERGY, 5).add(Aspect.TAINT, 5).add(Aspect.AURA, 5), 4, 3, 3, new ItemStack(TMBlocks.nodeGenerator)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.NODEGENERATOR.1"), new ResearchPage("techno.research_page.NODEGENERATOR.2"), new ResearchPage((InfusionRecipe)recipes.get("NodeGenerator"))}).setParents(new String[] { "CONDENSER" }).setSpecial().registerResearchItem();
		
		new ResearchItem("PEN", "TECHNOMANCY", new AspectList(), 4, -4, 0, new ItemStack(TMItems.itemPen)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.PEN.1"), new ResearchPage((IRecipe)recipes.get("Pen")), new ResearchPage((IRecipe)recipes.get("PenCore"))}).setParents(new String[] {}).setAutoUnlock().registerResearchItem();
		
		new ResearchItem("BIOMEMORPHER", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.TAINT, 5).add(Aspect.AURA, 5).add(Aspect.EARTH, 5), -3, 0, 3, new ItemStack(TMBlocks.biomeMorpher)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.BIOMEMORPHER.1"),  new ResearchPage((IArcaneRecipe)recipes.get("BiomeMorpher"))}).setParents(new String[] { "PROCESSOR" }).setSecondary().registerResearchItem();

		new ResearchItem("TESLACOIL", "TECHNOMANCY", new AspectList().add(Aspect.EXCHANGE, 5).add(Aspect.ELDRITCH, 5).add(Aspect.MECHANISM, 5).add(Aspect.AURA, 5), 3, -3, 3, new ItemStack(TMBlocks.teslaCoil)).setPages(new ResearchPage[] {new ResearchPage("techno.research_page.TESLACOIL.1"), new ResearchPage("techno.research_page.TESLACOIL.2"), new ResearchPage((IArcaneRecipe)recipes.get("TeslaCoil")), new ResearchPage((IArcaneRecipe)recipes.get("CoilCoupler"))}).setParents(new String[] { "QUANTUMJARS"}).setRound().registerResearchItem();

		new ResearchItem("FLUXLAMP", "TECHNOMANCY", new AspectList().add(Aspect.ORDER, 5).add(Aspect.MECHANISM, 5).add(Aspect.EXCHANGE, 5).add(Aspect.WATER, 5), -4, -1, 2, new ItemStack(TMBlocks.fluxLamp)).setPages(new ResearchPage[] {new ResearchPage("techno.research_page.FLUXLAMP.1"), new ResearchPage((InfusionRecipe)recipes.get("FluxLamp"))}).setParents(new String[] { "PROCESSOR"}).setRound().registerResearchItem();
	
		new ResearchItem("ENERGIZEDWAND", "TECHNOMANCY", new AspectList().add(Aspect.TOOL, 5).add(Aspect.MAGIC, 5).add(Aspect.ENERGY, 5).add(Aspect.EXCHANGE, 5), -1, 2, 3, new ItemStack(TMItems.itemWandCores, 1, 0)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.ELECTRICWAND.1"), new ResearchPage((InfusionRecipe)recipes.get("EnergizedWandRod"))}).setParents(new String[] { "TECHNOBASICS" }).setHidden().setItemTriggers(new ItemStack(ConfigItems.itemWandRod, 1, 1)).registerResearchItem();
	
		new ResearchItem("ELECTRICBELLOWS", "TECHNOMANCY", new AspectList().add(Aspect.ENERGY, 5).add(Aspect.AIR, 5).add(Aspect.FIRE, 5), -3, -2, 2, new ItemStack(TMBlocks.electricBellows)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.ELECTRICBELLOWS.1"), new ResearchPage((IArcaneRecipe)recipes.get("ElectricBellows"))}).setParents(new String[]  {"PROCESSOR", "BELLOWS"}).registerResearchItem();
		
		new ResearchItem("CONDENSER", "TECHNOMANCY", new AspectList().add(Aspect.ENERGY, 5).add(Aspect.ORDER, 5).add(Aspect.EXCHANGE, 5), 2, 3, 3, new ItemStack(TMBlocks.condenserBlock)).setPages(new ResearchPage [] { new ResearchPage("techno.research_page.CONDENSER.1"), new ResearchPage((InfusionRecipe)recipes.get("Condenser"))}).setParents(new String[] {"DYNAMO"}).registerResearchItem();
	
		new ResearchItem("PROCESSOR", "TECHNOMANCY", new AspectList().add(Aspect.FIRE, 5).add(Aspect.EXCHANGE, 5).add(Aspect.ENTROPY, 5), -2, -1, 3, new ItemStack(TMBlocks.processorTC)).setPages(new ResearchPage[] { new ResearchPage("techno.research_page.PROCESSOR.1"), new ResearchPage((IArcaneRecipe)recipes.get("Processor"))}).setParents(new String[] {"TECHNOBASICS"}).registerResearchItem();
	}
	
}
