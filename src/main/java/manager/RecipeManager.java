package manager;

import factory.database.ConnectionFactory;
import model.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;

import static model.mapping.tables.RecipeTable.RECIPE;

/**
 * Created by wendywang on 2015-11-08.
 */

public class RecipeManager {

    RecipeInstructionManager instructionManager = new RecipeInstructionManager();
    RecipeIngredientManager ingredientManager = new RecipeIngredientManager();

    public List<Recipe> getRecipes() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<Recipe> recipeList = create.select().from(RECIPE).fetchInto(Recipe.class);
            attachRecipeInstruction(recipeList);
            attachRecipeIngredient(recipeList);
            return recipeList;
        }
    }

    private void attachRecipeInstruction(List<Recipe> recipeList) throws Exception {
        for (Recipe recipe: recipeList) {
            recipe.setRecipeInstructionList(instructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
        }
    }

    private void attachRecipeIngredient(List<Recipe> recipeList) throws Exception {
        for (Recipe recipe: recipeList) {
            recipe.setRecipeIngredientList(ingredientManager.getRecipeIngredientByRecipeId(recipe.getRecipeId()));
        }
    }

    public Recipe getRecipeByID(int recipeID) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeID)).fetchAny().into(Recipe.class);
        }
    }

    public boolean addRecipe(Recipe recipe) {
        boolean recipeCreated = true;
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
            instructionManager.addRecipeInstructionList(recipeRecord.getRecipeId(), recipe.getRecipeInstructionList());
        } catch (Exception exception) {
            recipeCreated = false;
        }
        return recipeCreated;
    }

}
