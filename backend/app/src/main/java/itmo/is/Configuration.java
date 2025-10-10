package itmo.is;

import itmo.is.dao.LocationDAO;
import itmo.is.dao.RouteDAO;
import itmo.is.service.LocationService;
import itmo.is.service.RouteService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class Configuration extends AbstractBinder {
    @Override
    protected void configure() {
        bind(RouteDAO.class).to(RouteDAO.class);
        bind(LocationDAO.class).to(LocationDAO.class);
        bind(RouteService.class).to(RouteService.class);
        bind(LocationService.class).to(LocationService.class);
    }
}
