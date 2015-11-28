package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeIngredient extends model.mapping.tables.pojos.RecipeIngredient {
    public RecipeIngredient(){}
    public RecipeIngredient(Integer recipeID, Integer ingredientId, String name, String amount, String imageURL) {
        setName(name);
        setImageUrl(imageURL);
        setRecipeId(recipeID);
        setAmount(amount);
        setIngredientId(ingredientId);
    }

    @Override
    @JsonIgnore
    public Integer getRecipeId() {
        return super.getRecipeId();
    }

    @Override
    @JsonIgnore
    public void setRecipeId(Integer recipeId) {
        super.setRecipeId(recipeId);
    }

    @Override
    @JsonProperty("ingredient_id")
    public Integer getIngredientId() {
        return super.getIngredientId();
    }

    @Override
    @JsonProperty("ingredient_id")
    public void setIngredientId(Integer ingredientId) {
        super.setIngredientId(ingredientId);
    }

    @Override
    @JsonProperty("name")
    public String getName() {
        return super.getName();
    }

    @Override
    @JsonProperty("name")
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    @JsonProperty("amount")
    public String getAmount() {
        return super.getAmount();
    }

    @Override
    @JsonProperty("amount")
    public void setAmount(String amount) {
        super.setAmount(amount);
    }

    @Override
    @JsonProperty("image_url")
    public String getImageUrl() {
        return super.getImageUrl();
    }

    @Override
    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        super.setImageUrl(imageUrl);
    }

}
