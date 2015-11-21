package manager;

import factory.database.ConnectionFactory;
import model.RecipeInstruction;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;

import static model.mapping.tables.RecipeInstructionTable.RECIPE_INSTRUCTION;

/**
 * Created by wendywang on 2015-11-14.
 */
public class RecipeInstructionManager {

    public List<RecipeInstruction> getRecipeInstructionListByRecipeId(Integer recipeId) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from(RECIPE_INSTRUCTION).where(RECIPE_INSTRUCTION.RECIPE_ID.equal(recipeId)).fetchInto(RecipeInstruction.class);
        }
    }

}
