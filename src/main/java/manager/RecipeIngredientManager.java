package manager;

import exception.DatabaseException;
import factory.database.DataObjectFactory;
import model.RecipeIngredient;

import java.util.List;

import static model.mapping.tables.RecipeIngredientTable.RECIPE_INGREDIENT;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeIngredientManager {

    public List<RecipeIngredient> getRecipeIngredientByRecipeId(Integer recipeId) throws DatabaseException {
        return DataObjectFactory.getDataObjectList(RECIPE_INGREDIENT, RECIPE_INGREDIENT.RECIPE_ID.equal(recipeId), RecipeIngredient.class);
    }

    public void addRecipeIngredientList(Integer recipeId, List<RecipeIngredient> recipeIngredientList) throws DatabaseException {
        recipeIngredientList.stream().forEach(ingredient -> ingredient.setRecipeId(recipeId));
        DataObjectFactory.storeDataObjectList(RECIPE_INGREDIENT, recipeIngredientList);
    }

    public void putRecipeIngredientList(List<RecipeIngredient> ingredientListUpdate) throws DatabaseException {
        DataObjectFactory.updateDataObjectList(RECIPE_INGREDIENT, ingredientListUpdate);
    }

}
