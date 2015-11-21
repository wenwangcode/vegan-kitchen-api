package manager;

import factory.database.ConnectionFactory;
import model.mapping.tables.pojos.IngredientRecipe;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import static model.mapping.tables.IngredientRecipeTable.INGREDIENT_RECIPE;

/**
 * Created by wendywang on 2015-11-14.
 */
public class IngredientRecipeManager {

    public List<IngredientRecipe> getIngredientRecipeByRecipeId(Integer recipeId) throws Exception
    {
        try (Connection connection = ConnectionFactory.getConnection())
        {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            return create.select().from().where(INGREDIENT_RECIPE.RECIPE_ID.equal(recipeId)).fetchInto(IngredientRecipe.class);
        }
    }



}
