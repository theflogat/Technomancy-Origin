package democretes.handlers;

import java.io.File;

import democretes.lib.BlockIds;
import democretes.lib.ItemIds;
import democretes.lib.LibNames;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigHandler {
	
	public static boolean fancy = true;
	public static int[] blacklist = {};
	public static boolean compatiblity = true;
	public static boolean thaumcraft = false;
	public static boolean bloodmagic = false;
	public static boolean botania = false;
	public static boolean bonus = true;

    public static void Init(File file) {
        Configuration config = new Configuration(file);

        config.load();
        
        //Blocks
        BlockIds.idNODE_DYNAMO = config.getBlock(LibNames.NODE_DYNAMO_NAME, BlockIds.idNODE_DYNAMO_DEFAULT).getInt();
        BlockIds.idESSENTIA_CONTAINER = config.getBlock(LibNames.ESSENTIA_CONTAINER_NAME, BlockIds.idESSENTIA_CONTAINER_DEFAULT).getInt();
        BlockIds.idCOSMETIC_OPAQUE = config.getBlock(LibNames.COSMETIC_OPAQUE_NAME, BlockIds.idCOSMETIC_OPAQUE_DEFAULT).getInt();
        BlockIds.idCOSMETIC_PANE = config.getBlock(LibNames.COSMETIC_PANE_NAME, BlockIds.idCOSMETIC_PANE_DEFAULT).getInt();
        BlockIds.idESSENTIA_DYNAMO = config.getBlock(LibNames.ESSENTIA_DYNAMO_NAME, BlockIds.idESSENTIA_DYNAMO_DEFAULT).getInt();
        BlockIds.idBIOME_MORPHER = config.getBlock(LibNames.BIOME_MORPHER_NAME, BlockIds.idBIOME_MORPHER_DEFAULT).getInt();
        BlockIds.idNODE_GENERATOR = config.getBlock(LibNames.NODE_GENERATOR_NAME, BlockIds.idNODE_GENERATOR_DEFAULT).getInt();
        BlockIds.idFLUX_LAMP = config.getBlock(LibNames.FLUX_LAMP_NAME, BlockIds.idFLUX_LAMP_DEFAULT).getInt();
        BlockIds.idTESLA_COIL = config.getBlock(LibNames.TESLA_COIL_NAME, BlockIds.idTESLA_COIL_DEFALULT).getInt();
        BlockIds.idELECTRIC_BELLOWS = config.getBlock(LibNames.ELECTRIC_BELLOWS_NAME, BlockIds.idELECTRIC_BELLOWS_DEFAULT).getInt();
        BlockIds.idCREATIVE_JAR = config.getBlock(LibNames.CREATIVE_JAR_NAME, BlockIds.idCREATIVE_JAR_DEFAULT).getInt();
        BlockIds.idCRYSTAL_BLOCK = config.getBlock(LibNames.CRYSTAL_NAME, BlockIds.idCRYSTAL_BLOCK_DEFAULT).getInt();
        BlockIds.idRECONSTRUCTOR = config.getBlock(LibNames.RECONSTRUCTOR_NAME, BlockIds.idRECONSTRUCTOR_DEFAULT).getInt();
        BlockIds.idBLOOD_DYNAMO = config.getBlock(LibNames.BLOOD_DYNAMO_NAME, BlockIds.idBLOOD_DYNAMO_DEFAULT).getInt();
        BlockIds.idCONDENSER = config.getBlock(LibNames.CONDENSER_NAME, BlockIds.idCONDENSER_DEFAULT).getInt();
        BlockIds.idBLOOD_FABRICATOR = config.getBlock(LibNames.BLOOD_FABRICATOR_NAME, BlockIds.idBLOOD_FABRICATOR_DEFAULT).getInt();
        BlockIds.idFLOWER_DYNAMO = config.getBlock(LibNames.FLOWER_DYNAMO_NAME, BlockIds.idFLOWER_DYNAMO_DEFAULT).getInt();


        //Items        
        ItemIds.idESSENTIA_CANNON = config.getItem(LibNames.ESSENTIA_CANNON_NAME, ItemIds.idESSENTIA_CANNON_DEFAULT).getInt();
        ItemIds.idITEM_MATERIAL = config.getItem(LibNames.ITEM_MATERIAL_NAME, ItemIds.idITEM_MATERIAL_DEFAULT).getInt();
        ItemIds.idITEM_PEN = config.getItem(LibNames.ITEM_PEN_NAME, ItemIds.idITEM_PEN_DEFAULT).getInt();
        ItemIds.idITEM_WAND_CORES = config.getItem(LibNames.ITEM_WAND_CORES_NAME, ItemIds.idITEM_WAND_CORES_DEFAULT).getInt();
        ItemIds.idITEM_FUSION_FOCUS = config.getItem(LibNames.ITEM_FUSION_FOCUS_NAME, ItemIds.idITEM_FUSION_FOCUS_DEFAULT).getInt();

        //Recipe Whatnots
        Property smelting = config.get("Recipes", "Add/Increase Smelting bonus to dusts/ore", bonus);
        bonus = smelting.getBoolean(true);
        
        //Render effects
        Property coilfx = config.get("Renderers", "CoilFX", fancy);
        fancy = coilfx.getBoolean(true);
        
        //Reconstructor stuff
        System.out.println("Configurating");
        Property reconstructor = config.get("Machines", "Blacklisted materials for use in the Essentia Reconstructor", blacklist);
        blacklist = reconstructor.getIntList();
        
        //Compatibility Stuff
        Property check = config.get("Compatibility", "Check for other mods", compatiblity);
        compatiblity = check.getBoolean(true);
        
        Property tc = config.get("Compatibility", "Enables Thaumcraft", thaumcraft);
        thaumcraft = tc.getBoolean(thaumcraft);
        
        Property bm = config.get("Compatibility", "Enable Blood Magic", bloodmagic);
        bloodmagic = bm.getBoolean(bloodmagic);
        
        Property bo = config.get("Compatibility", "Enable Botania", botania);
        botania = bo.getBoolean(botania);

        config.save();

    }

}
