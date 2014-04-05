package democretes.handlers;

import java.io.File;

import democretes.lib.BlockIds;
import democretes.lib.ItemIds;
import democretes.lib.LibNames;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class ConfigHandler {
	
	public static boolean fancy = true;

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

        //Items
        
        ItemIds.idESSENTIA_CANNON = config.getItem(LibNames.ESSENTIA_CANNON_NAME, ItemIds.idESSENTIA_CANNON_DEFAULT).getInt();
        ItemIds.idITEM_MATERIAL = config.getItem(LibNames.ITEM_MATERIAL_NAME, ItemIds.idITEM_MATERIAL_DEFAULT).getInt();
        ItemIds.idITEM_PEN = config.getItem(LibNames.ITEM_PEN_NAME, ItemIds.idITEM_PEN_DEFAULT).getInt();
        ItemIds.idITEM_WAND_CORES = config.getItem(LibNames.ITEM_WAND_CORES_NAME, ItemIds.idITEM_WAND_CORES_DEFAULT).getInt();
        ItemIds.idITEM_FUSION_FOCUS = config.getItem(LibNames.ITEM_FUSION_FOCUS_NAME, ItemIds.idITEM_FUSION_FOCUS_DEFAULT).getInt();
        
        //Render effects
        Property coilfx = config.get("Renderers", "CoilFX", fancy);
        fancy = coilfx.getBoolean(true);

        config.save();

    }

}
