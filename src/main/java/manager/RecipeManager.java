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

    RecipeInstructionManager recipeInstructionManager = new RecipeInstructionManager();

    public List<Recipe> getRecipes() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<Recipe> recipeList = create.select().from(RECIPE).fetchInto(Recipe.class);
            attachRecipeInstruction(recipeList);
            return recipeList;
        }
    }

    private void attachRecipeInstruction(List<Recipe> recipeList) throws Exception {
        for (Recipe recipe: recipeList) {
            recipe.setRecipeInstructionList(recipeInstructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
        }
    }

    public Recipe getRecipeByID(int recipeID) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeID)).fetchAny().into(Recipe.class);
        }
    }

    public Recipe addRecipe(Recipe recipe) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
            recipe.setRecipeId(recipeRecord.getRecipeId());
        }
        return recipe;
    }

}
