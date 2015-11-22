package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendywang on 2015-11-14.
 */
public class Ingredient extends model.mapping.tables.pojos.IngredientRecipe {
    public Ingredient(){}
    public Ingredient(String name, int recipeID, String imageURL, int ingredientID)
    {
        setName(name);
        setImageUrl(imageURL);
        setRecipeId(recipeID);
        setIngredientId(ingredientID);
    }

    @Override
    @JsonProperty
    public Integer getIngredientId()
    {
        return super.getIngredientId();
    }

    @Override
    @JsonProperty
    public void setIngredientId(Integer ingredientId)
    {
        super.setIngredientId(ingredientId);
    }

    @Override
    @JsonProperty
    public Integer getRecipeId()
    {
        return super.getRecipeId();
    }

    @Override
    @JsonProperty
    public void setRecipeId(Integer recipeId)
    {
        super.setRecipeId(recipeId);
    }

    @Override
    @JsonProperty
    public String getName()
    {
        return super.getName();
    }

    @Override
    @JsonProperty
    public void setName(String name)
    {
        super.setName(name);
    }

    @Override
    @JsonProperty
    public String getImageUrl()
    {
        return super.getImageUrl();
    }

    @Override
    @JsonProperty
    public void setImageUrl(String imageUrl)
    {
        super.setImageUrl(imageUrl);
    }





}
