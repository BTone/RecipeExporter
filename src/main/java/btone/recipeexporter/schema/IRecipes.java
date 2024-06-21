package btone.recipeexporter.schema;

import java.util.Map;

public interface IRecipes {

    Map<String, IRecipeGroup> getRecipeGroups();

    IRecipes put(String K, IRecipeGroup V);

    IRecipes putAllRecipeGroups(Map<String, IRecipeGroup> recipeGroups);
}
