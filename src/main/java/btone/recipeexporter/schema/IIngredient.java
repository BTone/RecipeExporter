package btone.recipeexporter.schema;

public interface IIngredient {

    String getName();

    IIngredient setName(String name);

    long getAmount();

    IIngredient setAmount(long amount);
}
