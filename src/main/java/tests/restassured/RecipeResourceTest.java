package restassured;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.TestUtility;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

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
    public void testHasCorrectDishId(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.recipe_id", equalTo(1));
    }

    @Test
    public void testHasCorrectDishName(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.dish_name", equalTo("dish name 1"));
    }

    @Test
    public void testHasCorrectServingSize(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.serving", equalTo("serving 1"));
    }

    @Test
    public void testHasCorrectSummary(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.summary", equalTo("summary 1"));
    }

    @Test
    public void testHasCorrectDishImage(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.dish_image_url", equalTo("dish.image.1.com"));
    }

    @Test
    public void testHasCorrectUserId(){
        when()
                .get("http://localhost:8080/recipe/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("result.author_id", equalTo(1));
    }

//    @Test
//    public void testNumberOfRecipes(){
//        when()
//                .get("http://localhost:8080/recipe").
//        then()
//                .assertThat()
//                .statusCode(200)
//                .body(("keySet().size()"),equalTo(2));
//    }

    






}
