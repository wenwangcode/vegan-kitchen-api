package manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InvalidUpdateException;
import factory.database.ConnectionFactory;
import model.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.HashMap;
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
        Integer createdRecipeId;
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
            createdRecipeId = recipeRecord.getRecipeId();
            instructionManager.addRecipeInstructionList(createdRecipeId, recipe.getRecipeInstructionList());
            ingredientManager.addRecipeIngredientList(createdRecipeId, recipe.getRecipeIngredientList());
        }
        return getRecipeByID(createdRecipeId);
    }

    public Recipe updateRecipe(Integer recipeId, Recipe recipeUpdate) throws Exception {
        Recipe exitingRecipe = getRecipeByID(recipeId);
        Recipe updatedRecipe = getUpdatedRecipe(exitingRecipe, recipeUpdate);
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, updatedRecipe);
            create.executeUpdate(recipeRecord);
            // TODO: update instructions
            // TODO: update ingredients
        }
        return getRecipeByID(recipeId);
    }

    /**
     * get an updated version of existingRecipe based on non-null fields stored in recipeUpdate
     * @param existingRecipe : existing recipe to be updated
     * @param recipeUpdate : recipe object that carries the update data
     * @return : updated recipe
     * @throws InvalidUpdateException : thrown when attempt to update immutable field / primary key (i.e. recipe_id)
     */
    private Recipe getUpdatedRecipe(Recipe existingRecipe, Recipe recipeUpdate) throws InvalidUpdateException{
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> exitingRecipeMap = objectMapper.convertValue(existingRecipe, new TypeReference<HashMap<String, Object>>(){});
        HashMap<String, Object> recipeUpdateMap = objectMapper.convertValue(recipeUpdate, new TypeReference<HashMap<String, Object>>(){});
        for (String key : recipeUpdateMap.keySet()) {
            Object updateValue = recipeUpdateMap.get(key);
            if (updateValue != null) {
                if (key.equals(RECIPE.RECIPE_ID.getName())) {
                    throw new InvalidUpdateException("Attempt to update immutable primary key: " + RECIPE.RECIPE_ID.getName());
                }
                exitingRecipeMap.put(key, recipeUpdateMap.get(key));
            }
        }
        return objectMapper.convertValue(exitingRecipeMap, new TypeReference<Recipe>(){});
    }

}
