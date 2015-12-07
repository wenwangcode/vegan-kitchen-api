package manager;

import exception.DatabaseException;
import exception.InvalidUpdateException;
import factory.database.ConnectionFactory;
import model.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static model.mapping.tables.RecipeTable.RECIPE;

/**
 * Created by wendywang on 2015-11-08.
 */

public class RecipeManager {

    RecipeInstructionManager instructionManager = new RecipeInstructionManager();
    RecipeIngredientManager ingredientManager = new RecipeIngredientManager();

    public List<Recipe> getRecipes() throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<Recipe> recipeList = create.select().from(RECIPE).fetchInto(Recipe.class);
            attachRecipeInstruction(recipeList);
            attachRecipeIngredient(recipeList);
            return recipeList;
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataRetrievalErrorMessage("failed to retrieve all recipes."));
        }
    }

    private void attachRecipeInstruction(List<Recipe> recipeList) throws DatabaseException {
        for (Recipe recipe: recipeList) {
            recipe.setRecipeInstructionList(instructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
        }
    }

    private void attachRecipeIngredient(List<Recipe> recipeList) throws DatabaseException {
        for (Recipe recipe: recipeList) {
            recipe.setRecipeIngredientList(ingredientManager.getRecipeIngredientByRecipeId(recipe.getRecipeId()));
        }
    }

    public Recipe getRecipeById(int recipeId) throws DatabaseException {
        try(Connection connection = ConnectionFactory.getConnection()) {
            Recipe returnRecipe;
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            returnRecipe = create.select().from(RECIPE).where(RECIPE.RECIPE_ID.equal(recipeId)).fetchAny().into(Recipe.class);
            attachRecipeInstruction(returnRecipe);
            attachRecipeIngredient(returnRecipe);
            return returnRecipe;
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataRetrievalErrorMessage("failed to retrieve recipe by id."));
        }
    }

    private void attachRecipeInstruction(Recipe recipe) throws DatabaseException {
        recipe.setRecipeInstructionList(instructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
    }

    private void attachRecipeIngredient(Recipe recipe) throws DatabaseException {
        recipe.setRecipeIngredientList(ingredientManager.getRecipeIngredientByRecipeId(recipe.getRecipeId()));
    }

    public Recipe addRecipe(Recipe recipe) throws DatabaseException {
        Integer createdRecipeId;
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            RecipeRecord recipeRecord = create.newRecord(RECIPE, recipe);
            recipeRecord.store();
            createdRecipeId = recipeRecord.getRecipeId();
            instructionManager.addRecipeInstructionList(createdRecipeId, recipe.getRecipeInstructionList());
            ingredientManager.addRecipeIngredientList(createdRecipeId, recipe.getRecipeIngredientList());
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataCreationErrorMessage("failed to add recipe."));
        }
        return getRecipeById(createdRecipeId);
    }

    public Recipe updateRecipe(Integer recipeId, Recipe recipeUpdate) throws DatabaseException, InvalidUpdateException {
        if (isValidRecipeUpdate(recipeId, recipeUpdate)) {
            try (Connection connection = ConnectionFactory.getConnection()) {
                DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
                RecipeRecord recipeRecord = create.newRecord(RECIPE, recipeUpdate);
                create.batchUpdate(recipeRecord).execute();
                instructionManager.putRecipeInstructionList(recipeUpdate.getRecipeInstructionList());
                ingredientManager.putRecipeIngredientList(recipeUpdate.getRecipeIngredientList());
            }
            catch (SQLException|DataAccessException exception) {
                throw new DatabaseException(DatabaseException.getDataUpdateErrorMessage("failed to update recipe."));
            }
        }
        return getRecipeById(recipeId);
    }

    private boolean isValidRecipeUpdate(Integer recipeId, Recipe recipeUpdate) throws InvalidUpdateException {
        try {
            Recipe existingRecipe = getRecipeById(recipeId);
            if (containRecipeIdUpdate(recipeUpdate)) {
                throw new InvalidUpdateException(InvalidUpdateException.getInvalidUpdateDataMessage("attempt to update recipe id."));
            }
            else if (!isConsistentInstructionIdSet(existingRecipe.getRecipeInstructionIdSet(), recipeUpdate.getRecipeInstructionIdSet())) {
                throw new InvalidUpdateException(InvalidUpdateException.getInvalidUpdateDataMessage("instruction id set not found."));
            }
            else if (!isConsistentIngredientIdSet(existingRecipe.getRecipeIngredientIdSet(), recipeUpdate.getRecipeIngredientIdSet())) {
                throw new InvalidUpdateException(InvalidUpdateException.getInvalidUpdateDataMessage("ingredient id set not found."));
            }
            return true;
        }
        catch (DatabaseException|DataAccessException exception) {
            throw new InvalidUpdateException(InvalidUpdateException.getInvalidUpdateDataMessage("failed to validate the passed in recipe data."));
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
