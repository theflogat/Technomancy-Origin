package democretes.handlers;

import cpw.mods.fml.common.Loader;

public class CompatibilityHandler {
		
	public static void checkThisShit() {
		if(ConfigHandler.compatiblity) {
			if(Loader.isModLoaded("AWWayofTime")) {
				ConfigHandler.bloodmagic = true;
				System.out.println("Nazi super science activated");
			}
			if(Loader.isModLoaded("Botania")) {
				ConfigHandler.botania = true;
				System.out.println("Hippy scientists encountered");
			}
			if(Loader.isModLoaded("Thaumcraft")) {
				ConfigHandler.thaumcraft = true;
				System.out.println("Alkahestry has been discovered");
			}
			ConfigHandler.compatiblity = false;
		}
	}

}
