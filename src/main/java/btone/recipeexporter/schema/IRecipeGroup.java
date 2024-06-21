package btone.recipeexporter.schema;

import java.util.List;

public interface IRecipeGroup {

    List<IRecipe> getRecipes();

    IRecipeGroup addRecipes(IRecipe recipe);
}
