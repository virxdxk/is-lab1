package itmo.is.service;

import itmo.is.dao.LocationDAO;
import itmo.is.entities.Location;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class LocationService {
    @Inject
    private LocationDAO locationDAO;

    public List<Location> getAllLocations() {
        return locationDAO.findAll();
    }

    public Location getLocationById(Long id) {
        return locationDAO.find(id);
    }

    public void createLocation(Location location) {
        locationDAO.create(location);
    }

    public void updateLocation(Location location) {
        locationDAO.update(location);
    }

    public void deleteLocation(Long id) {
        locationDAO.delete(id);
    }
}
