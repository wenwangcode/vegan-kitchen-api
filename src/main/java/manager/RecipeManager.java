package manager;

import exception.DatabaseException;
import exception.InvalidUpdateException;
import factory.database.DataObjectFactory;
import model.Recipe;
import org.jooq.exception.DataAccessException;

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
        List<Recipe> recipeList = DataObjectFactory.getDataObjectList(RECIPE, Recipe.class);
        attachRecipeInstruction(recipeList);
        attachRecipeIngredient(recipeList);
        return recipeList;
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
        Recipe recipe = DataObjectFactory.getDataObject(RECIPE, RECIPE.RECIPE_ID.equal(recipeId), Recipe.class);
        attachRecipeInstruction(recipe);
        attachRecipeIngredient(recipe);
        return recipe;
    }

    private void attachRecipeInstruction(Recipe recipe) throws DatabaseException {
        recipe.setRecipeInstructionList(instructionManager.getRecipeInstructionListByRecipeId(recipe.getRecipeId()));
    }

    private void attachRecipeIngredient(Recipe recipe) throws DatabaseException {
        recipe.setRecipeIngredientList(ingredientManager.getRecipeIngredientByRecipeId(recipe.getRecipeId()));
    }

    public Recipe addRecipe(Recipe recipe) throws DatabaseException {
        Integer createdRecipeId = DataObjectFactory.storeDataObject(RECIPE, recipe, RECIPE.RECIPE_ID);
        instructionManager.addRecipeInstructionList(createdRecipeId, recipe.getRecipeInstructionList());
        ingredientManager.addRecipeIngredientList(createdRecipeId, recipe.getRecipeIngredientList());
        return getRecipeById(createdRecipeId);
    }

    public Recipe updateRecipe(Integer recipeId, Recipe recipeUpdate) throws DatabaseException, InvalidUpdateException {
        if (isValidRecipeUpdate(recipeId, recipeUpdate)) {
            DataObjectFactory.updateDataObject(RECIPE, recipeUpdate);
            instructionManager.putRecipeInstructionList(recipeUpdate.getRecipeInstructionList());
            ingredientManager.putRecipeIngredientList(recipeUpdate.getRecipeIngredientList());
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
