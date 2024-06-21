package btone.recipeexporter;

import java.io.File;

import cpw.mods.fml.relauncher.FMLInjectionData;

public class Util {

    static public File getMinecraftDirectory() {
        return ((File) FMLInjectionData.data()[6]);
    }
}
