package btone.recipeexporter.schema;

public class Ingredient implements IIngredient {

    public String name;
    public long amount;

    public Ingredient(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient() {
        this("", 0);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public IIngredient setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public IIngredient setAmount(long amount) {
        this.amount = amount;
        return this;
    }
}
