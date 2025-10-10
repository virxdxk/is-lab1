package itmo.is;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class App extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(itmo.is.controller.RouteController.class);
        classes.add(itmo.is.controller.LocationController.class);
        classes.add(itmo.is.CorsFilter.class);
        classes.add(itmo.is.CorsRequestFilter.class);
        return classes;
    }
    
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        singletons.add(new Configuration());
        return singletons;
    }
}
