package btone.recipeexporter.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class Recipe implements IRecipe {

    public List<IIngredient> inputs = new ArrayList<IIngredient>();
    public List<IIngredient> outputs = new ArrayList<IIngredient>();
    public Long duration = null;
    public Long eut = null;

    public Recipe() {

    }

    public Recipe(List<IIngredient> inputs, List<IIngredient> outputs, long duration, long eut) {
        this.inputs.addAll(inputs);
        this.outputs.addAll(outputs);
        this.duration = duration;
        this.eut = eut;
    }

    @Override
    public List<IIngredient> getInputs() {
        return inputs;
    }

    @Override
    public IRecipe addInputs(IIngredient input) {
        inputs.add(input);
        return this;
    }

    @Override
    public List<IIngredient> getOutputs() {
        return outputs;
    }

    @Override
    public IRecipe addOutputs(IIngredient output) {
        outputs.add(output);
        return this;
    }

    @Override
    public OptionalLong getDuration() {
        return duration == null ? OptionalLong.empty() : OptionalLong.of(duration);
    }

    @Override
    public IRecipe setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public IRecipe setEut(long eut) {
        this.eut = eut;
        return this;
    }

    @Override
    public OptionalLong getEut() {
        return eut == null ? OptionalLong.empty() : OptionalLong.of(eut);
    }
}
