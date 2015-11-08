package manager;

import model.Recipe;

/**
 * Created by wendywang on 2015-11-08.
 */

public class MockRecipeManager extends RecipeManager {


    public MockRecipeManager() {
        super();
    }

    @Override
    public String getDishNameByID(int id) {
        String dishName = new String();
        String imageURL = new String();
        String summary = new String();
        int dishID = 0;
        int servingSize = 0;
        Recipe recipe = new Recipe(dishName, dishID, imageURL, servingSize, summary);
//        if (id == recipe.getId()) {
//            return recipe.getDishName();
//        }
        return "Chicken";
    }

    @Override
    public Recipe getRecipeByID(int id) {
        Recipe recipe = new Recipe("chicken", 123, "URL", 1, "Summary");
        //if (id == recipe.getId()) {
            //return recipe;
        //}
        return recipe;
    }

}
