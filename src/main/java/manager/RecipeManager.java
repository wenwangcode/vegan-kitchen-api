package manager;

import com.sun.media.sound.InvalidDataException;
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
            Recipe returnRecipe;
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            returnRecipe = create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeID)).fetchAny().into(Recipe.class);
            attachRecipeInstruction(returnRecipe);
            attachRecipeIngredient(returnRecipe);
            return returnRecipe;
        }
    }

    private void attachRecipeInstruction(Recipe recipe) throws Exception {
        recipe.setRecipeInstructionList(instructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
    }

    private void attachRecipeIngredient(Recipe recipe) throws Exception {
        recipe.setRecipeIngredientList(ingredientManager.getRecipeIngredientByRecipeId(recipe.getRecipeId()));
    }

    public Recipe addRecipe(Recipe recipe) throws Exception {
        Integer createRecipeId;
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
            createRecipeId = recipeRecord.getRecipeId();
            instructionManager.addRecipeInstructionList(createRecipeId, recipe.getRecipeInstructionList());
            ingredientManager.addRecipeIngredientList(createRecipeId, recipe.getRecipeIngredientList());
        }
        return getRecipeByID(createRecipeId);
    }

    public Recipe updateRecipe(Integer recipeId, Recipe recipeUpdate) throws Exception {
        Recipe exitingRecipe = getRecipeByID(recipeId);
        updateFields(exitingRecipe, recipeUpdate);
        return null; // TODO
    }

    private void updateFields(Recipe exitingRecipe, Recipe recipeUpdate) throws Exception{
        if (recipeUpdate.getRecipeId() != null && exitingRecipe.getRecipeId().equals(recipeUpdate.getRecipeId()))
            throw new InvalidDataException("Attempt to update existing recipe id");
        if (recipeUpdate.getDishName() != null)
            exitingRecipe.setDishName(recipeUpdate.getDishName());
        if (recipeUpdate.getSummary() != null)
            exitingRecipe.setSummary(recipeUpdate.getSummary());
        if (recipeUpdate.getServing() != null)
            exitingRecipe.setServing(recipeUpdate.getServing());
        if (recipeUpdate.getDishImageUrl() != null)
            exitingRecipe.setDishImageUrl(recipeUpdate.getDishImageUrl());
        if (recipeUpdate.getAuthorUserId() != null)
            exitingRecipe.setAuthorUserId(recipeUpdate.getAuthorUserId());
    }

}
