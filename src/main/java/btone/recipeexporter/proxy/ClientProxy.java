package btone.recipeexporter.proxy;

import btone.recipeexporter.client.KeyBindingHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    // Override CommonProxy methods here, if you want a different behaviour
    // on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        // Register key binds and corresponding event handlers
        KeyBindingHandler.preInit();
    }

}
