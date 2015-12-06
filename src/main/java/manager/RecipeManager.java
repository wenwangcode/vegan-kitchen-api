package manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InvalidDataException;
import exception.InvalidUpdateException;
import factory.database.ConnectionFactory;
import model.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import static model.mapping.tables.RecipeTable.RECIPE;

/**
 * Created by wendywang on 2015-11-08.
 */

public class RecipeManager {

    ObjectMapper objectMapper = new ObjectMapper();
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
        catch (Exception exception) {
            throw new DataAccessException("Failed to get recipes due to: " + exception.getMessage());
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

    public Recipe getRecipeById(int recipeId) throws Exception {
        try(Connection connection = ConnectionFactory.getConnection()) {
            Recipe returnRecipe;
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            returnRecipe = create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeId)).fetchAny().into(Recipe.class);
            attachRecipeInstruction(returnRecipe);
            attachRecipeIngredient(returnRecipe);
            return returnRecipe;
        }
        catch (Exception exception) {
            throw new DataAccessException("Failed to get recipe with recipe id [" + recipeId + "] due to: " + exception.getMessage());
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
        catch (Exception exception) {
            throw new InvalidDataException("Failed to create recipe due to: " + exception.getMessage());
        }
        return getRecipeById(createdRecipeId);
    }

    public Recipe updateRecipe(Integer recipeId, Recipe recipeUpdate) throws Exception {
        if (isValidRecipeUpdate(recipeId, recipeUpdate)) {
            try (Connection connection = ConnectionFactory.getConnection()) {
                DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
                RecipeRecord recipeRecord = create.newRecord(RECIPE, recipeUpdate);
                create.batchUpdate(recipeRecord).execute();
                instructionManager.putRecipeInstructionList(recipeUpdate.getRecipeInstructionList());
                ingredientManager.putRecipeIngredientList(recipeUpdate.getRecipeIngredientList());
            } catch (Exception exception) {
                throw new InvalidDataException("Failed to update recipe with recipe id [" + recipeId + "] due to: " + exception.getMessage());
            }
        }
        return getRecipeById(recipeId);
    }

    private boolean isValidRecipeUpdate(Integer recipeId, Recipe recipeUpdate) throws Exception {
        try {
            Recipe existingRecipe = getRecipeById(recipeId);
            if (containRecipeIdUpdate(recipeUpdate)) {
                throw new InvalidUpdateException("Invalid recipe update: attempt to update immutable recipe id.");
            }
            else if (!isConsistentInstructionIdSet(existingRecipe.getRecipeInstructionIdSet(), recipeUpdate.getRecipeInstructionIdSet())) {
                throw new InvalidUpdateException("Invalid recipe update: instruction id(s) not found.");
            }
            else if (!isConsistentIngredientIdSet(existingRecipe.getRecipeIngredientIdSet(), recipeUpdate.getRecipeIngredientIdSet())) {
                throw new InvalidUpdateException("Invalid recipe update: ingredient id(s) not found.");
            }
            return true;
        }
        catch (DataAccessException dataAccessException) {
            throw new InvalidUpdateException("Invalid recipe update: no recipe id[" + recipeId + "] found.");
        }
    }

    private boolean containRecipeIdUpdate(Recipe recipeUpdate) {
        return recipeUpdate.getRecipeId() != null &&
            recipeUpdate.getRecipeInstructionList().stream().anyMatch(instruction -> instruction.getRecipeId() != null) &&
            recipeUpdate.getRecipeIngredientList().stream().anyMatch(ingredient -> ingredient.getRecipeId() != null);
    }

    private boolean isConsistentInstructionIdSet(Set<Integer> existingInstructionSet, Set<Integer> instructionUpdateSet) {
        return existingInstructionSet.containsAll(instructionUpdateSet);
    }

    private boolean isConsistentIngredientIdSet(Set<Integer> existingIngredientSet, Set<Integer> ingredientUpdateSet) {
        return existingIngredientSet.containsAll(ingredientUpdateSet);
    }

}
