package btone.recipeexporter.schema;

import java.util.Map;
import java.util.TreeMap;

public class Recipes implements IRecipes {

    public Map<String, IRecipeGroup> recipeGroups = new TreeMap<>();

    public Recipes() {}

    public Recipes(Map<String, IRecipeGroup> recipeGroups) {
        this.recipeGroups.putAll(recipeGroups);
    }

    @Override
    public Map<String, IRecipeGroup> getRecipeGroups() {
        return recipeGroups;
    }

    @Override
    public IRecipes put(String K, IRecipeGroup V) {
        recipeGroups.put(K, V);
        return this;
    }

    @Override
    public IRecipes putAllRecipeGroups(Map<String, IRecipeGroup> recipeGroups) {
        this.recipeGroups.putAll(recipeGroups);
        return this;
    }
}
