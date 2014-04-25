package democretes.items;

import thaumcraft.api.wands.WandRod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import democretes.lib.ItemIds;
import democretes.lib.LibNames;
import democretes.lib.Reference;

public class TMItems {

    public static void initThaumcraft() {

        // Item Initializations
        //essentiaCannon = new ItemEssentiaCannon(ItemIds.idESSENTIA_CANNON);
        itemMaterial = new ItemMaterial(ItemIds.idITEM_MATERIAL);
        itemPen = new ItemPen(ItemIds.idITEM_PEN);
        itemWandCores = new ItemWandCores(ItemIds.idITEM_WAND_CORES);
        itemFusionFocus = new ItemFusionFocus(ItemIds.idITEM_FUSION_FOCUS);
        
        //Registry
        //GameRegistry.registerItem(essentiaCannon, LibNames.ESSENTIA_CANNON_NAME);
        GameRegistry.registerItem(itemMaterial, LibNames.ITEM_MATERIAL_NAME);
        GameRegistry.registerItem(itemPen, LibNames.ITEM_PEN_NAME);
        GameRegistry.registerItem(itemFusionFocus, LibNames.ITEM_FUSION_FOCUS_NAME);
        
        
        GameRegistry.registerItem(itemWandCores, LibNames.ITEM_WAND_CORES_NAME);
        WAND_ROD_ELECTRIC = new WandRod("electric", 25, new ItemStack(itemWandCores, 1, 0), 9, new ElectricWandUpdate(), new ResourceLocation("technom", "textures/models/electricWand.png"));


    }

    // Item Instances
    public static Item essentiaCannon;
    public static Item itemMaterial;
    public static Item itemPen;
    public static Item itemWandCores;
    public static Item itemFusionFocus;
    
    public static WandRod WAND_ROD_ELECTRIC;

}
