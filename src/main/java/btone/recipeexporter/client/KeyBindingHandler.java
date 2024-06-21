package btone.recipeexporter.client;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import btone.recipeexporter.RecipeExporter;
import btone.recipeexporter.exporters.Exporters;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyBindingHandler {

    public static final Map<KeyBinding, Consumer<KeyInputEvent>> isPressedHandlers = new HashMap<>();

    private static final KeyBinding export = new KeyBinding("Dump Crafting GSON", Keyboard.KEY_X, "Recipe Exporter");

    private static KeyBindingHandler instance;

    static {
        // isPressed handlers for key binds
        isPressedHandlers.put(export, KeyBindingHandler::exportHandler);
    }

    private KeyBindingHandler() {
        // Register key binds
        for (var binding : isPressedHandlers.keySet()) {
            ClientRegistry.registerKeyBinding(binding);
        }

        // Register events
        FMLCommonHandler.instance()
            .bus()
            .register(this);

        RecipeExporter.LOG.info(String.format("Registered %d key binds", KeyBindingHandler.isPressedHandlers.size()));
    }

    public static void preInit() {
        // All relevant initialization is handled by the constructor, so just
        // creating the singleton instance here will suffice.
        instance();
    }

    private static void exportHandler(KeyInputEvent event) {
        Exporters.export();
    }

    public static KeyBindingHandler instance() {
        if (instance == null) instance = new KeyBindingHandler();

        return instance;
    }

    @SubscribeEvent
    public void keyInputHandler(KeyInputEvent event) {
        for (var binding : isPressedHandlers.keySet()) {
            if (binding.isPressed()) isPressedHandlers.get(binding)
                .accept(event);
        }
    }
}
