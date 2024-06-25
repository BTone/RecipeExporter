package btone.recipeexporter.exporters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import btone.recipeexporter.RecipeExporter;
import btone.recipeexporter.Util;
import btone.recipeexporter.schema.IRecipes;
import btone.recipeexporter.schema.Recipes;

public class Exporters {

    private static final String EXPORT_FILE_BASENAME = "recipes";

    private static File createOutFile(String ext) {
        return new File(Util.getMinecraftDirectory(), EXPORT_FILE_BASENAME + ext);
    }

    private static void write(ObjectMapper mapper, File outFile, IRecipes recipes) throws IOException {
        if (mapper instanceof CBORMapper) {
            try (var writer = new FileOutputStream(outFile)) {
                mapper.writeValue(writer, recipes);
            }
        } else {
            try (var writer = new FileWriter(outFile)) {
                mapper.writeValue(writer, recipes);
            }
        }
    }

    private static void exportJson(IRecipes recipes) throws IOException {
        var outFile = createOutFile(".json");
        write(new JsonMapper().registerModule(new Jdk8Module()), outFile, recipes);
        RecipeExporter.LOG.info(String.format("Wrote recipes to %s", outFile));
    }

    private static void exportCbor(IRecipes recipes) throws IOException {
        var outFile = createOutFile(".cbor");
        write(new CBORMapper().registerModule(new Jdk8Module()), outFile, recipes);
        RecipeExporter.LOG.info(String.format("Wrote recipes to %s", outFile));
    }

    public static void export() {
        try {
            var recipes = new Recipes();

            recipes.putAllRecipeGroups(ShapedCraftingExporter.export());
            recipes.putAllRecipeGroups(GregTechExporter.export());

            exportJson(recipes);
            exportCbor(recipes);
        } catch (Exception e) {
            RecipeExporter.LOG.error("Something bad happened", e);
        }
    }
}
