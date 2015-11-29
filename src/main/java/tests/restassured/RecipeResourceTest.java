package restassured;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Recipe;
import model.RecipeIngredient;
import model.RecipeInstruction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utility.TestUtility;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by adam on 22/11/15.
 */
public class RecipeResourceTest {

    private static final String POST_RECIPE_JSON_FILE = "post_recipe.json";
    private static TestUtility testUtility = new TestUtility();

    @BeforeClass
    public static void setUp() throws Exception {
        testUtility.resetDataBase();
        testUtility.runSQLScript("recipe_resource_test.sql");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        testUtility.resetDataBase();
    }

    @Test
    public void testAllRecipeOKStatus() {
        when()
            .get("http://localhost:8080/recipe/all")
        .then()
            .assertThat()
                .statusCode(SC_OK);
    }

    @Test
    public void testHasCorrectDishId() {
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.recipe_id", equalTo(1));
    }

    @Test
    public void testHasCorrectDishName() {
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.dish_name", equalTo("dish name 1"));
    }

    @Test
    public void testHasCorrectServingSize() {
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.serving", equalTo("serving 1"));
    }

    @Test
    public void testHasCorrectSummary() {
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.summary", equalTo("summary 1"));
    }

    @Test
    public void testHasCorrectDishImage() {
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.dish_image_url", equalTo("dish.image.1.com"));
    }

    @Test
    public void testHasCorrectUserId(){
        when()
            .get("http://localhost:8080/recipe/1")
        .then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.author_id", equalTo(1));
    }

    @Test
    public void testNumberOfRecipes(){
        when()
            .get("http://localhost:8080/recipe/all").
        then()
            .assertThat()
                .statusCode(SC_OK)
                .body("result.size()",equalTo(2));
    }

    @Test
    public void testPostRecipe() throws Exception {
        String authorization = "";  // TODO: require login function
        String expectedPostRecipeJsonString = testUtility.getStringFromJson(POST_RECIPE_JSON_FILE);
        Recipe expectedPostRecipe = (Recipe) testUtility.getPojoFromJson(POST_RECIPE_JSON_FILE, new TypeReference<Recipe>(){});
        Recipe actualPostRecipe = assertSuccessfulRecipeCreation(authorization, expectedPostRecipeJsonString);
        assertEqualRecipeContent(expectedPostRecipe, actualPostRecipe);
    }

    private Recipe assertSuccessfulRecipeCreation(String authorization, String recipeJsonString) throws Exception {
        Map<String, Object> createdRecipeMap =
            given()
                .header("authorization", authorization)
                .contentType("application/json")
                .body(recipeJsonString)
            .when()
                .post("http://localhost:8080/recipe/")
            .then()
                .assertThat()
                    .statusCode(SC_CREATED)
                .extract()
                    .response().path("result");
        return (Recipe) testUtility.getPojoFromMap(createdRecipeMap, new TypeReference<Recipe>(){});
    }

    private void assertEqualRecipeContent(Recipe expectedRecipe, Recipe actualRecipe) {
        assertEqualString(expectedRecipe.getDishName(), actualRecipe.getDishName());
        assertEqualString(expectedRecipe.getSummary(), actualRecipe.getSummary());
        assertEqualString(expectedRecipe.getServing(), actualRecipe.getServing());
        assertEqualString(expectedRecipe.getDishImageUrl(), actualRecipe.getDishImageUrl());
        assertEqualInteger(expectedRecipe.getAuthorUserId(), actualRecipe.getAuthorUserId());
        assertEqualInstructionListContent(expectedRecipe.getRecipeInstructionList(), actualRecipe.getRecipeInstructionList());
        assertEqualIngredientListContent(expectedRecipe.getRecipeIngredientList(), actualRecipe.getRecipeIngredientList());
    }

    private void assertEqualInstructionListContent(List<RecipeInstruction> expectedInstructionList, List<RecipeInstruction> actualInstructionList) {
        if (expectedInstructionList.size() != actualInstructionList.size()) {
            Assert.fail("Expected " + expectedInstructionList.size() + " recipe instruction record(s), but found " + actualInstructionList.size());
        }
        for (int i = 0; i < actualInstructionList.size(); i++) {
            assertEqualInstructionContent(expectedInstructionList.get(i), actualInstructionList.get(i));
        }
    }

    private void assertEqualInstructionContent(RecipeInstruction expectedInstruction, RecipeInstruction actualInstruction) {
        assertEqualString(expectedInstruction.getInstruction(), actualInstruction.getInstruction());
        assertEqualInteger(expectedInstruction.getStepNumber(), actualInstruction.getStepNumber());
        assertEqualString(expectedInstruction.getImageUrl(), actualInstruction.getImageUrl());
    }

    private void assertEqualIngredientListContent(List<RecipeIngredient> expectedIngredientList, List<RecipeIngredient> actualIngredientList) {
        if (expectedIngredientList.size() != actualIngredientList.size()) {
            Assert.fail("Expected " + expectedIngredientList.size() + " recipe instruction record(s), but found " + actualIngredientList.size());
        }
        for (int i = 0; i < actualIngredientList.size(); i++) {
            assertEqualIngredientContent(expectedIngredientList.get(i), actualIngredientList.get(i));
        }
    }

    private void assertEqualIngredientContent(RecipeIngredient expectedIngredient, RecipeIngredient actualIngredient) {
        assertEqualString(expectedIngredient.getName(), actualIngredient.getName());
        assertEqualString(expectedIngredient.getAmount(), actualIngredient.getAmount());
        assertEqualString(expectedIngredient.getImageUrl(), actualIngredient.getImageUrl());
    }

    private void assertEqualString(String expectedString, String actualString) {
        if ((expectedString == null && actualString != null) ||
                (expectedString != null && actualString == null) ||
                !expectedString.equals(actualString))
            Assert.fail("Expected " + expectedString + " but found " + actualString);
    }

    private void assertEqualInteger(Integer expectedInteger, Integer actualInteger) {
        if ((expectedInteger != null && actualInteger == null) ||
                (expectedInteger == null && actualInteger != null) ||
                !expectedInteger.equals(actualInteger))
            Assert.fail("Expected " + String.valueOf(expectedInteger) + " but found " + String.valueOf(actualInteger));
    }

}
