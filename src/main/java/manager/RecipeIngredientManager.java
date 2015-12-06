package manager;

import exception.InvalidDataException;
import factory.database.ConnectionFactory;
import model.RecipeIngredient;
import model.mapping.tables.records.RecipeIngredientRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static model.mapping.tables.RecipeIngredientTable.RECIPE_INGREDIENT;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeIngredientManager {

    public List<RecipeIngredient> getRecipeIngredientByRecipeId(Integer recipeId) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE_INGREDIENT).where(RECIPE_INGREDIENT.RECIPE_ID.equal(recipeId)).fetchInto(RecipeIngredient.class);
        }
    }

    public void addRecipeIngredientList(Integer recipeId, List<RecipeIngredient> recipeIngredientList) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeIngredientRecord> ingredientRecordList = recipeIngredientList.stream()
                .map(ingredient -> {
                    ingredient.setRecipeId(recipeId);
                    return create.newRecord(RECIPE_INGREDIENT, ingredient);})
                .collect(Collectors.toList());
            create.batchInsert(ingredientRecordList).execute();
        }
        catch (Exception exception) {
            throw new InvalidDataException("Failed to add recipe ingredients to recipe id [" + recipeId + "] due to: " + exception.getMessage());
        }
    }

    public void putRecipeIngredientList(List<RecipeIngredient> ingredientListUpdate) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeIngredientRecord> ingredientRecordList = ingredientListUpdate.stream()
                .map(ingredient -> create.newRecord(RECIPE_INGREDIENT, ingredient))
                .collect(Collectors.toList());
            create.batchUpdate(ingredientRecordList).execute();
        }
        catch (Exception exception) {
            throw new InvalidDataException("Failed to update recipe ingredients due to: " + exception.getMessage());
        }
    }

}
