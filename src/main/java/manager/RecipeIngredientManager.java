package manager;

import exception.DatabaseException;
import factory.database.ConnectionFactory;
import model.RecipeIngredient;
import model.mapping.tables.records.RecipeIngredientRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static model.mapping.tables.RecipeIngredientTable.RECIPE_INGREDIENT;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeIngredientManager {

    public List<RecipeIngredient> getRecipeIngredientByRecipeId(Integer recipeId) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE_INGREDIENT).where(RECIPE_INGREDIENT.RECIPE_ID.equal(recipeId)).fetchInto(RecipeIngredient.class);
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataRetrievalErrorMessage("failed to retrieve recipe ingredient list."));
        }
    }

    public void addRecipeIngredientList(Integer recipeId, List<RecipeIngredient> recipeIngredientList) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeIngredientRecord> ingredientRecordList = recipeIngredientList.stream()
                .map(ingredient -> {
                    ingredient.setRecipeId(recipeId);
                    return create.newRecord(RECIPE_INGREDIENT, ingredient);})
                .collect(Collectors.toList());
            create.batchInsert(ingredientRecordList).execute();
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataCreationErrorMessage("failed to add recipe ingredient list."));
        }
    }

    public void putRecipeIngredientList(List<RecipeIngredient> ingredientListUpdate) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeIngredientRecord> ingredientRecordList = ingredientListUpdate.stream()
                .map(ingredient -> create.newRecord(RECIPE_INGREDIENT, ingredient))
                .collect(Collectors.toList());
            create.batchUpdate(ingredientRecordList).execute();
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataUpdateErrorMessage("failed to update recipe ingredient list."));
        }
    }

}
