package btone.recipeexporter.schema;

import java.util.ArrayList;
import java.util.List;

public class RecipeGroup implements IRecipeGroup {

    public List<IRecipe> recipes = new ArrayList<>();

    public String catalystName = null;

    public RecipeGroup() {}

    public RecipeGroup(List<IRecipe> recipes) {
        this.recipes.addAll(recipes);
    }

    @Override
    public List<IRecipe> getRecipes() {
        return recipes;
    }

    @Override
    public IRecipeGroup addRecipes(IRecipe recipe) {
        recipes.add(recipe);
        return this;
    }

    @Override
    public String getCatalystName() {
        return catalystName;
    }

    @Override
    public IRecipeGroup setCatalystName(String catalystName) {
        this.catalystName = catalystName;
        return this;
    }
}
