package restassured;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Recipe;
import model.RecipeIngredient;
import model.RecipeInstruction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import utility.TestUtility;

import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.*;

/**
 * Created by adam on 22/11/15.
 */
public class RecipeResourceTest {

    // URL
    private static final String BASE_URL = "http://localhost:8080/recipe/";

    // test data
    private static final String TEST_DATA_SETUP_SCRIPT = "recipe_resource_test.sql";
    private static final String POST_RECIPE_JSON_FILE = "post_recipe.json";
    private static final String GET_ALL_RECIPES_JSON_FILE = "get_all_recipes.json";
    private static final String GET_RECIPE_JSON_FILE = "get_recipe.json";
    private static final String PUT_RECIPE_JSON_FILE = "put_recipe.json";

    private static TestUtility testUtility = new TestUtility();

    @BeforeClass
    public static void setUp() throws Exception {
        testUtility.resetTestData(TEST_DATA_SETUP_SCRIPT);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        testUtility.resetDataBase();
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        testUtility.resetTestData(TEST_DATA_SETUP_SCRIPT);
        List<Recipe>  actualRecipeList = assertSuccessfulAllRecipesAccess();
        List<Recipe> expectedRecipeList = (List<Recipe>) testUtility.getPojoFromFile(GET_ALL_RECIPES_JSON_FILE, new TypeReference<List<Recipe>>(){});
        for (int i = 0; i < actualRecipeList.size(); i++) {
            assertEqualRecipeContent(expectedRecipeList.get(i), actualRecipeList.get(i));
        }
    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe expectedGetRecipe = (Recipe) testUtility.getPojoFromFile(GET_RECIPE_JSON_FILE, new TypeReference<Recipe>(){});
        Recipe actualGetRecipe = assertSuccessfulRecipeAccess(expectedGetRecipe.getRecipeId());
        assertEqualRecipeContent(expectedGetRecipe, actualGetRecipe);
    }

    @Test
    public void testPostRecipe() throws Exception {
        String authorization = "";  // TODO: require login function
        String expectedPostRecipeJsonString = testUtility.getStringFromFile(POST_RECIPE_JSON_FILE);
        Recipe expectedPostRecipe = (Recipe) testUtility.getPojoFromFile(POST_RECIPE_JSON_FILE, new TypeReference<Recipe>(){});
        Recipe actualPostRecipe = assertSuccessfulRecipeCreation(authorization, expectedPostRecipeJsonString);
        assertEqualRecipeContent(expectedPostRecipe, actualPostRecipe);
    }

    @Test
    public void testPutRecipe() throws Exception {
        String authorization = ""; // TODO: require login function
        String putRecipeJsonString = testUtility.getStringFromFile(PUT_RECIPE_JSON_FILE);
        Recipe expectedPutRecipe = (Recipe) testUtility.getPojoFromFile(PUT_RECIPE_JSON_FILE, new TypeReference<Recipe>(){});
        Recipe actualPutRecipe = assertSuccessPutRecipeUpdate(authorization, expectedPutRecipe.getRecipeId(), putRecipeJsonString);
        assertEqualRecipeContent(expectedPutRecipe, actualPutRecipe);
    }

    private List<Recipe> assertSuccessfulAllRecipesAccess() throws Exception {
        List<Map<String, Object>> recipeMapList =
                when()
                        .get(BASE_URL + "all/")
                        .then()
                        .assertThat()
                        .statusCode(SC_OK)
                        .extract()
                        .response().path("result");
        return (List<Recipe>) testUtility.convertObjectByReferenceType(recipeMapList, new TypeReference<List<Recipe>>(){});
    }

    private void assertFailAllRecipesAccess(int expectedStatusCode) throws Exception {
        when()
            .get(BASE_URL + "all/")
        .then()
            .assertThat()
                .statusCode(expectedStatusCode);
    }

    private Recipe assertSuccessfulRecipeAccess(int recipeId) throws Exception {
        Map<String, Object> recipeMap =
            when()
                .get(BASE_URL + String.valueOf(recipeId))
            .then()
                .assertThat()
                    .statusCode(SC_OK)
                .extract()
                    .response().path("result");
        return (Recipe) testUtility.convertObjectByReferenceType(recipeMap, new TypeReference<Recipe>(){});
    }

    private Recipe assertSuccessfulRecipeCreation(String authorization, String recipeJsonString) throws Exception {
        Map<String, Object> createdRecipeMap =
            given()
                .header("authorization", authorization)
                .contentType("application/json")
                .body(recipeJsonString)
            .when()
                .post(BASE_URL)
            .then()
                .assertThat()
                    .statusCode(SC_CREATED)
                .extract()
                    .response().path("result");
        return (Recipe) testUtility.convertObjectByReferenceType(createdRecipeMap, new TypeReference<Recipe>(){});
    }

    private Recipe assertSuccessPutRecipeUpdate(String authorization, int recipeId, String recipeJsonString) throws Exception {
        Map<String, Object> putRecipeMap =
            given()
                .header("authorization", authorization)
                .contentType("application/json")
                .body(recipeJsonString)
            .when()
                .put(BASE_URL + String.valueOf(recipeId))
            .then()
                .assertThat()
                    .statusCode(SC_OK)
                .extract()
                    .response().path("result");
        return (Recipe) testUtility.convertObjectByReferenceType(putRecipeMap, new TypeReference<Recipe>(){});
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
