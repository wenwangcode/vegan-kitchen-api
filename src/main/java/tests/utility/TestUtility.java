package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibatis.common.jdbc.ScriptRunner;
import model.mapping.tables.pojos.Recipe;
import model.mapping.tables.records.RecipeRecord;
import org.codehaus.plexus.util.IOUtil;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import static model.mapping.tables.RecipeTable.RECIPE;

/**
 * Created by adam on 22/11/15.
 */
public class TestUtility {

    private String TEST_DATA_PATH = "../resources/";
    private String PROPERTIES_FILE = "testconfig/test.config.properties";
    private String connectionUrl;
    private String mysqlConnectorDriver;
    private String mysqlUser;
    private String mysqlPassword;

    private void loadConfiguration() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        Properties properties = new Properties();
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        connectionUrl = properties.getProperty("mysql_url");
        mysqlConnectorDriver = properties.getProperty("mysql_connector_driver");
        mysqlUser = properties.getProperty("mysql_user");
        mysqlPassword = properties.getProperty("mysql_password");
    }

    public Connection getDatabaseConnection() throws Exception{
        loadConfiguration();
        try{
            Class.forName(mysqlConnectorDriver).newInstance();
            return DriverManager.getConnection(connectionUrl, mysqlUser, mysqlPassword);
        }
        catch (Exception exception){
            exception.printStackTrace();
            throw exception;
        }
    }

    public void resetDataBase() throws Exception{
        runSQLScript("reset.sql");
    }

    public void runSQLScript(String sqlScript) throws Exception{
        try (Connection connection = getDatabaseConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
            InputStream scriptInputStream = this.getClass().getClassLoader().getResourceAsStream("resources/data_scripts/" + sqlScript);
            InputStreamReader inputStreamReader = new InputStreamReader(scriptInputStream);
            scriptRunner.runScript(inputStreamReader);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Map<String, Object> getData(String fileName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = IOUtil.toString(this.getClass().getResourceAsStream(TEST_DATA_PATH + fileName));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
        return dataMap;
    }

    public void populateRecipeTable(List<Map<String, Object>> recipeData) throws Exception {
        List<Recipe> recipeList = parseRecipeData(recipeData);
        List<RecipeRecord> recipeRecordList = new ArrayList<>();
        try (Connection connection = getDatabaseConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.MYSQL);
            for (Recipe recipe: recipeList) {
                recipeRecordList.add(create.newRecord(RECIPE, recipe));
            }
            create.batchInsert(recipeRecordList).execute();
        }
    }

    private List<Recipe> parseRecipeData(List<Map<String, Object>> recipeData) throws Exception{
        List<Recipe> recipeList = new ArrayList<>();
        try {
            for (Map<String, Object> recipeMap : recipeData) {
                Recipe recipe = new Recipe();
                recipe.setDishName((String) recipeMap.get("dish_name"));
                recipe.setSummary((String) recipeMap.get("summary"));
                recipe.setServing((String) recipeMap.get("serving"));
                recipe.setDishImageUrl((String) recipeMap.get("dish_image_url"));
                recipe.setAuthorUserId((Integer) recipeMap.get("author_user_id"));
                recipeList.add(recipe);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return recipeList;
    }

}
