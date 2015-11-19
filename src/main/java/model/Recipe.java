package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wendywang on 2015-11-07.
 */
@XmlRootElement
public class Recipe extends model.mapping.tables.pojos.Recipe{

    public Recipe(){};

    public Recipe(Integer recipeId, String dishName, String summary, String servingSize, String dishImageUrl, int authorUserId) {
        setRecipeId(recipeId);
        setDishName(dishName);
        setSummary(summary);
        setServing(servingSize);
        setDishImageUrl(dishImageUrl);
        setAuthorUserId(authorUserId);
    }

    @Override
    @JsonProperty("recipe_id")
    public Integer getRecipeId() {
        return super.getRecipeId();
    }

    @Override
    @JsonProperty("recipe_id")
    public void setRecipeId(Integer recipeId) {
        super.setRecipeId(recipeId);
    }

    @Override
    @JsonProperty("dish_name")
    public String getDishName() {
        return super.getDishName();
    }

    @Override
    @JsonProperty("dish_name")
    public void setDishName(String dishName) {
        super.setDishName(dishName);
    }

    @Override
    @JsonProperty("summary")
    public String getSummary() {
        return super.getSummary();
    }

    @Override
    @JsonProperty("summary")
    public void setSummary(String summary) {
        super.setSummary(summary);
    }

    @Override
    @JsonProperty("serving")
    public String getServing() {
        return super.getServing();
    }

    @Override
    @JsonProperty("serving")
    public void setServing(String serving) {
        super.setServing(serving);
    }

    @Override
    @JsonProperty("dish_image_url")
    public String getDishImageUrl() {
        return super.getDishImageUrl();
    }

    @Override
    @JsonProperty("dish_image_url")
    public void setDishImageUrl(String dishImageURL) {
        super.setDishImageUrl(dishImageURL);
    }

    @Override
    @JsonProperty("author_id")
    public Integer getAuthorUserId(){
        return super.getAuthorUserId();
    }

    @Override
    @JsonProperty("author_id")
    public void setAuthorUserId(Integer authorUserID){
        super.setAuthorUserId(authorUserID);
    }
}