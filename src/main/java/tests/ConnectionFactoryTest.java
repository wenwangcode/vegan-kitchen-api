package tests;

import database.utility.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
/**
 * Created by wendywang on 2015-11-14.
 */
public class ConnectionFactoryTest {
    @Test
    public void testGetConnection(){
        try {
            Assert.assertTrue("Failed to get db connection, returned null pointer", ConnectionFactory.getConnection() != null);
        }
        catch (Exception exception) {
            Assert.fail("Failed to get db connection, error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

}
