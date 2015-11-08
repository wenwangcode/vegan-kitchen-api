package application;

import net.gazsi.laszlo.sandbox.ws.RestExample;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wendywang on 2015-11-07.
 */
@ApplicationPath("/")
public class MyApplication extends Application{

    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(RestExample.class);
        return s;
    }

}
