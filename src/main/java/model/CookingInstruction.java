package model;

/**
 * Created by wendywang on 2015-11-14.
 */
public class CookingInstruction {

    private int recipeID;
    private int instructionID;
    private String instruction;
    private String imageURL;
    public CookingInstruction(){}

    public CookingInstruction(int recipeID, int instructionID, String instruction, String step_description, String imageURL){

        this.recipeID = recipeID;
        this.instructionID = instructionID;
        this.instruction = instruction;
        this.imageURL = imageURL;

    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipe_id) {
        this.recipeID = recipeID;
    }

    public int getInstructionID() {
        return instructionID;
    }

    public void setInstructionID(int instruction_id) {
        this.instructionID = instructionID;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }
}
