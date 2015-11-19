package manager;

import factory.database.ConnectionFactory;
import model.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static model.mapping.tables.RecipeTable.RECIPE;

/**
 * Created by wendywang on 2015-11-08.
 */

public class RecipeManager {

    public List<Recipe> getRecipes() throws Exception {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            recipeList = create.select().from(RECIPE).fetchInto(Recipe.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return recipeList;
    }

    public Recipe getRecipeByID(int recipeID) throws Exception {
        Recipe recipe = null;
        try(Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            recipe = create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeID)).fetchAny().into(Recipe.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return recipe;
    }

    public Recipe addRecipe(Recipe recipe) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return recipe;
    }


}
