package btone.recipeexporter.exporters;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import btone.recipeexporter.RecipeExporter;
import btone.recipeexporter.schema.*;
import codechicken.nei.recipe.GuiCraftingRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.nei.GT_NEI_DefaultHandler;
import gregtech.nei.GT_NEI_DefaultHandler.CachedDefaultRecipe;

public class GregTechExporter {

    private GregTechExporter() {}

    static private List<GT_NEI_DefaultHandler> getGtNeiHandlers() {
        return GuiCraftingRecipe.craftinghandlers.stream()
            .filter(x -> x instanceof GT_NEI_DefaultHandler)
            .map(x -> (GT_NEI_DefaultHandler) x)
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

    static private Recipe createRecipe(CachedDefaultRecipe src) {
        var recipe = new Recipe();

        recipe.setDuration(src.mRecipe.mDuration);
        recipe.setEut(src.mRecipe.mEUt);

        // Inputs
        for (var input : Stream.concat(Arrays.stream(src.mRecipe.mInputs), Arrays.stream(src.mRecipe.mFluidInputs))
            .collect(Collectors.toList())) {
            if (input instanceof ItemStack i) {
                if (i.stackSize == 0) continue;
                recipe.addInputs(createIngredient(i));
            } else if (input instanceof FluidStack i) {
                if (i.amount == 0) continue;
                recipe.addInputs(createIngredient(i));
            }
        }

        // Outputs
        for (var output : Stream.concat(Arrays.stream(src.mRecipe.mOutputs), Arrays.stream(src.mRecipe.mFluidOutputs))
            .collect(Collectors.toList())) {
            if (output instanceof ItemStack i) {
                recipe.addOutputs(createIngredient(i));
            } else if (output instanceof FluidStack i) {
                recipe.addOutputs(createIngredient(i));
            }
        }

        return recipe;
    }

    static private RecipeGroup createRecipeGroup(GT_NEI_DefaultHandler src) {
        var recipeGroup = new RecipeGroup();

        // Populate recipes in handler
        src.arecipes.clear();
        src.loadCraftingRecipes(src.getRecipeMap().unlocalizedName);

        // Create recipes
        for (var recipe : src.arecipes) {
            if (recipe instanceof CachedDefaultRecipe r) {
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
        RecipeExporter.LOG.info("Exporting GregTech recipes...");

        var gtNeiHandlers = getGtNeiHandlers();

        var gtRecipeGroups = new TreeMap<String, IRecipeGroup>();

        for (var handler : gtNeiHandlers) {
            gtRecipeGroups.put(handler.getRecipeName(), createRecipeGroup(handler));
        }

        return gtRecipeGroups;
    }
}
