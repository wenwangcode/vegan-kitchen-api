package manager;

import exception.DatabaseException;
import factory.database.DataObjectFactory;
import model.RecipeInstruction;

import java.util.List;

import static model.mapping.tables.RecipeInstructionTable.RECIPE_INSTRUCTION;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeInstructionManager {

    public List<RecipeInstruction> getRecipeInstructionListByRecipeId(Integer recipeId) throws DatabaseException {
        return DataObjectFactory.getDataObjectList(RECIPE_INSTRUCTION, RECIPE_INSTRUCTION.RECIPE_ID.equal(recipeId), RecipeInstruction.class);
    }

    public void addRecipeInstructionList(Integer recipeId, List<RecipeInstruction> instructionList) throws DatabaseException {
        instructionList.stream().forEach(instruction -> instruction.setRecipeId(recipeId));
        DataObjectFactory.storeDataObjectList(RECIPE_INSTRUCTION, instructionList);
    }

    public void putRecipeInstructionList(List<RecipeInstruction> instructionListUpdate) throws DatabaseException {
        DataObjectFactory.updateDataObjectList(RECIPE_INSTRUCTION, instructionListUpdate);
    }

}
