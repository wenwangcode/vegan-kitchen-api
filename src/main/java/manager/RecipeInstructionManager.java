package manager;

import exception.DatabaseException;
import factory.database.ConnectionFactory;
import model.RecipeInstruction;
import model.mapping.tables.records.RecipeInstructionRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static model.mapping.tables.RecipeInstructionTable.RECIPE_INSTRUCTION;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeInstructionManager {

    public List<RecipeInstruction> getRecipeInstructionListByRecipeId(Integer recipeId) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE_INSTRUCTION).where(RECIPE_INSTRUCTION.RECIPE_ID.equal(recipeId)).fetchInto(RecipeInstruction.class);
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataRetrievalErrorMessage("failed to retrieve recipe instruction list."));
        }
    }

    public void addRecipeInstructionList(Integer recipeId, List<RecipeInstruction> instructionList) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeInstructionRecord> instructionRecordList = instructionList.stream()
                .map(instruction -> {
                    instruction.setRecipeId(recipeId);
                    return create.newRecord(RECIPE_INSTRUCTION, instruction);})
                .collect(Collectors.toList());
            create.batchInsert(instructionRecordList).execute();
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataCreationErrorMessage("failed to add recipe instruction list."));
        }
    }

    public void putRecipeInstructionList(List<RecipeInstruction> instructionListUpdate) throws DatabaseException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            List<RecipeInstructionRecord> instructionRecordList = instructionListUpdate.stream()
                .map(instruction -> create.newRecord(RECIPE_INSTRUCTION, instruction))
                .collect(Collectors.toList());
            create.batchUpdate(instructionRecordList).execute();
        }
        catch (SQLException|DataAccessException exception) {
            throw new DatabaseException(DatabaseException.getDataUpdateErrorMessage("failed to update recipe instruction list."));
        }
    }

}
