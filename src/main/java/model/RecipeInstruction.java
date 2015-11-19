package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wendywang on 2015-11-14.
 */
@XmlRootElement
public class RecipeInstruction extends model.mapping.tables.pojos.RecipeInstruction{

    public RecipeInstruction(Integer recipeID, Integer instructionID, String instruction, String imageURL){
        setRecipeId(recipeID);
        setInstructionId(instructionID);
        setInstruction(instruction);
        setImageUrl(imageURL);
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
    @JsonProperty("instruction_id")
    public Integer getInstructionId() {
        return super.getInstructionId();
    }

    @Override
    @JsonProperty("instruction_id")
    public void setInstructionId(Integer instructionId) {
        super.setInstructionId(instructionId);
    }

    @Override
    @JsonProperty("instruction")
    public String getInstruction() {
        return super.getInstruction();
    }

    @Override
    @JsonProperty("instruction")
    public void setInstruction(String instruction) {
        super.setInstruction(instruction);
    }

    @Override
    @JsonProperty("image_url")
    public String getImageUrl(){
        return super.getImageUrl();
    }

    @Override
    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl){
        super.setImageUrl(imageUrl);
    }
}
