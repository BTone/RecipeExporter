package btone.recipeexporter.schema;

import java.util.List;
import java.util.OptionalLong;

public interface IRecipe {

    List<IIngredient> getInputs();

    IRecipe addInputs(IIngredient input);

    List<IIngredient> getOutputs();

    IRecipe addOutputs(IIngredient input);

    OptionalLong getDuration();

    IRecipe setDuration(long duration);

    OptionalLong getEut();

    IRecipe setEut(long eut);
}
