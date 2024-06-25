package btone.recipeexporter.schema;

import java.util.List;

public interface IRecipeGroup {

    List<IRecipe> getRecipes();

    IRecipeGroup addRecipes(IRecipe recipe);

    String getCatalystName();

    IRecipeGroup setCatalystName(String catalystName);
}
