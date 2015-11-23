package restassured;

import com.ibatis.common.jdbc.ScriptRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.TestUtility;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.when;

/**
 * Created by adam on 22/11/15.
 */
public class RecipeResourceTest {

    private TestUtility testUtility = new TestUtility();
    private Map<String, Object> testData;

    @Before
    public void setUp() throws Exception{
        testData = testUtility.getData("data_prepopulation.json");
        testUtility.populateRecipeTable((List<Map<String, Object>>) ((Map<String, Object>) testData.get("get_all_recipes_test")).get("recipe_table"));
    }

    @After
    public void tearDown() throws Exception {
        try (Connection connection = testUtility.getDatabaseConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
            InputStream scriptInputStream = this.getClass().getClassLoader().getResourceAsStream("resources/reset.sql");
            InputStreamReader inputStreamReader = new InputStreamReader(scriptInputStream);
            scriptRunner.runScript(inputStreamReader);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Test
    public void testGetAllRecipe() {
        when()
            .get("http://localhost:8080/recipe/all")
        .then()
            .assertThat()
                .statusCode(200);
    }
}
