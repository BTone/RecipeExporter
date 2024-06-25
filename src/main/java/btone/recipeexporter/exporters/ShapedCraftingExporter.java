package btone.recipeexporter.exporters;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import btone.recipeexporter.RecipeExporter;
import btone.recipeexporter.schema.*;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipeTab;
import codechicken.nei.recipe.ShapedRecipeHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class ShapedCraftingExporter {

    private ShapedCraftingExporter() {}

    static private List<ShapedRecipeHandler> getHandlers() {
        return GuiCraftingRecipe.craftinghandlers.stream()
            .filter(x -> x.getClass() == ShapedRecipeHandler.class)
            .map(x -> (ShapedRecipeHandler) x)
            .collect(Collectors.toList());
    }

    static private IIngredient createIngredient(FluidStack i) {
        String name;

        try {
            name = i.getLocalizedName();
        } catch (Exception e) {
            RecipeExporter.LOG.error("Error getting localized name", e);
            name = i.getUnlocalizedName();
        }

        return new Ingredient().setName(name)
            .setAmount(i.amount);
    }

    static private IIngredient createIngredient(ItemStack i) {
        String name;

        try {
            name = i.getDisplayName();
        } catch (Exception e) {
            RecipeExporter.LOG.error("Error getting display name", e);
            name = GameRegistry.findUniqueIdentifierFor(i.getItem())
                .toString();
        }

        return new Ingredient().setName(name)
            .setAmount(i.stackSize);
    }

    static private Recipe createRecipe(ShapedRecipeHandler.CachedShapedRecipe src) {
        var recipe = new Recipe();

        // Inputs
        for (var input : src.getIngredients()) {
            var i = input.item;
            if (i.stackSize == 0) continue;
            recipe.addInputs(createIngredient(i));
        }

        // Outputs
        var o = src.getResult().item;
        recipe.addOutputs(createIngredient(o));

        return recipe;
    }

    static private RecipeGroup createRecipeGroup(ShapedRecipeHandler src) {
        var recipeGroup = new RecipeGroup();

        // Get catalyst from handler info
        var handlerInfo = GuiRecipeTab.getHandlerInfo(src);
        recipeGroup.setCatalystName(
            handlerInfo.getItemStack()
                .getDisplayName());

        // Populate recipes in handler
        src.arecipes.clear();
        src.loadCraftingRecipes("crafting");

        // Create recipes
        for (var recipe : src.arecipes) {
            if (recipe instanceof ShapedRecipeHandler.CachedShapedRecipe r) {
                try {
                    recipeGroup.addRecipes(createRecipe(r));
                } catch (Exception e) {
                    RecipeExporter.LOG.error("Error creating recipe, skipping", e);
                }
            }
        }

        return recipeGroup;
    }

    static public Map<String, IRecipeGroup> export() {
        RecipeExporter.LOG.info("Exporting shaped crafting recipes...");

        var handlers = getHandlers();

        var recipeGroups = new TreeMap<String, IRecipeGroup>();

        for (var handler : handlers) {
            recipeGroups.put(handler.getRecipeName(), createRecipeGroup(handler));
        }

        return recipeGroups;
    }
}
