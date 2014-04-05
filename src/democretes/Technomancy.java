package democretes;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import democretes.blocks.TMBlocks;
import democretes.handlers.ConfigHandler;
import democretes.handlers.CraftingHandler;
import democretes.handlers.ResearchHandler;
import democretes.items.TMItems;
import democretes.lib.CreativeTabTM;
import democretes.lib.Reference;
import democretes.network.PacketHandler;
import democretes.proxies.CommonProxy;

@Mod(modid = Reference.MOD_ID, 
	name = Reference.MOD_NAME, 
	version = Reference.MOD_VERSION, 
	dependencies = "required-after:Thaumcraft;required-after:ThermalExpansion;")

@NetworkMod(channels = { Reference.CHANNEL_NAME }, 
	clientSideRequired = true, serverSideRequired = false, 
	packetHandler = PacketHandler.class)


public class Technomancy {

    @Instance(Reference.MOD_ID)
    public static Technomancy instance;

    @SidedProxy(clientSide = "democretes.proxies.ClientProxy", serverSide = "democretes.proxies.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs tabsTM = new CreativeTabTM(
            CreativeTabs.getNextID(), Reference.MOD_ID);

    @EventHandler
    public void foreplay(FMLPreInitializationEvent event) {
        ConfigHandler.Init(new File(event.getModConfigurationDirectory(), "technomancy.cfg"));

        TMItems.init();
        TMBlocks.init();

    }

    @EventHandler
    public void orgasm(FMLInitializationEvent event) {
    	
    	proxy.initRenderers();

    }

    @EventHandler
    public void cuddling(FMLPostInitializationEvent event) {    	
    	CraftingHandler.init();
    	ResearchHandler.init();

    }

}
