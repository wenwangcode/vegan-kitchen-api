package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by wendywang on 2015-11-14.
 */
@XmlRootElement
public class RecipeInstruction extends model.mapping.tables.pojos.RecipeInstruction{

    public RecipeInstruction() {}

    public RecipeInstruction(Integer recipeID, Integer instructionID, Integer stepNumber, String instruction, String imageURL){
        setRecipeId(recipeID);
        setInstructionId(instructionID);
        setStepNumber(stepNumber);
        setInstruction(instruction);
        setImageUrl(imageURL);
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
    @JsonProperty("step_number")
    public Integer getStepNumber() {
        return super.getStepNumber();
    }

    @Override
    @JsonProperty("step_number")
    public void setStepNumber(Integer stepNumber) {
        super.setStepNumber(stepNumber);
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
