package btone.recipeexporter.schema;

import java.util.ArrayList;
import java.util.List;

public class RecipeGroup implements IRecipeGroup {

    public List<IRecipe> recipes = new ArrayList<>();

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
}
