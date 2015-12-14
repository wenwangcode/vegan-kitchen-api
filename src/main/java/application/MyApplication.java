package application;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wendywang on 2015-11-07.
 */
@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    public static final String PROPERTIES_FILE = "config.properties";
    public static Properties properties = new Properties();

    private Properties loadConfiguration() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return properties;
    }

    public MyApplication() {
        packages(true, "rest.api", "application.filter");
        loadConfiguration();
    }

}
