package restassured;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.TestUtility;

import static com.jayway.restassured.RestAssured.when;

/**
 * Created by adam on 22/11/15.
 */
public class RecipeResourceTest {

    private TestUtility testUtility = new TestUtility();

    @Before
    public void setUp() throws Exception{
        testUtility.resetDataBase();
        testUtility.runSQLScript("recipe_resource_test.sql");
    }

    @After
    public void tearDown() throws Exception {
        testUtility.resetDataBase();
    }

    @Test
    public void testAllRecipeOKStatus() {
        when()
            .get("http://localhost:8080/recipe/all")
        .then()
            .assertThat()
                .statusCode(200);
    }

    @Test
    public void testN() {
        when()
                .delete("http://localhost:8080/recipe/all")
                .then()
                .assertThat()
                .statusCode(204);
    }
}
