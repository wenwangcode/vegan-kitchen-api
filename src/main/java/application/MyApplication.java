package application;

import rest.api.RecipeResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by wendywang on 2015-11-07.
 */
@ApplicationPath("/")
public class MyApplication extends Application{

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

    @Override
    public Set<Class<?>> getClasses(){
        loadConfiguration();
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(RecipeResource.class);
        return resources;
    }

}
