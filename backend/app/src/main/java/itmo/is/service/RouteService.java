package itmo.is.service;

import itmo.is.entities.Route;
import itmo.is.dao.RouteDAO;
import jakarta.inject.Inject;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Date;

@Stateless
public class RouteService {

    @Inject
    private RouteDAO routeDAO;

    public void addRoute(Route route) {
        if (route.getDistance() <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0");
        }
        routeDAO.create(route);
    }

    public List<Route> getAllRoutes() {
        return routeDAO.findAll();
    }
    
    public Route getRouteById(Long id) {
        Route route = routeDAO.find(id);
        if (route == null) {
            throw new IllegalArgumentException("Route not found");
        }
        return route;
    }

    public void updateRoute(Route route) {
        if (route.getDistance() <= 0) {
            throw new IllegalArgumentException("Distance must be greater than 0");
        }
        routeDAO.update(route);
    }
    
    public void deleteRoute(Long id) {
        routeDAO.delete(id);
    }
}
