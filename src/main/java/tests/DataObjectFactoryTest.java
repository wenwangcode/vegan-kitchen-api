import factory.database.DataObjectFactory;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by wendywang on 2015-11-14.
 */
public class DataObjectFactoryTest {
    @Test
    public void testGetConnection(){
        try {
            Assert.assertTrue("Failed to get db connection, returned null pointer", DataObjectFactory.getDatabaseConnection() != null);
        }
        catch (Exception exception) {
            Assert.fail("Failed to get db connection, error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

}
