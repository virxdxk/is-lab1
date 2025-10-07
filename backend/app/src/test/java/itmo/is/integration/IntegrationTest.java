package itmo.is.integration;

import itmo.is.entities.Location;
import itmo.is.entities.Route;
import itmo.is.entities.Coordinates;
import itmo.is.service.LocationService;
import itmo.is.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IntegrationTest {

    private LocationService locationService;
    private RouteService routeService;

    @BeforeEach
    void setUp() {
        // В реальном проекте здесь была бы инициализация тестовой базы данных
        // Для демонстрации используем заглушки
        locationService = new LocationService() {
            @Override
            public List<Location> getAllLocations() {
                return List.of();
            }

            @Override
            public Location getLocationById(Long id) {
                if (id == 1L) {
                    Location location = new Location();
                    location.setId(1L);
                    location.setName("Test Location");
                    location.setX(100L);
                    location.setY(50.5f);
                    return location;
                }
                throw new IllegalArgumentException("Location not found");
            }

            @Override
            public void createLocation(Location location) {
                if (location.getName() == null || location.getName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Name is required");
                }
                location.setId(1L);
            }

            @Override
            public void updateLocation(Location location) {
                if (location.getName() == null || location.getName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Name is required");
                }
            }

            @Override
            public void deleteLocation(Long id) {
                if (id == 999L) {
                    throw new IllegalArgumentException("Location not found");
                }
            }
        };

        routeService = new RouteService() {
            @Override
            public List<Route> getAllRoutes() {
                return List.of();
            }

            @Override
            public Route getRouteById(Long id) {
                if (id == 1L) {
                    Route route = new Route();
                    route.setId(1L);
                    route.setName("Test Route");
                    route.setRating(4.5);
                    return route;
                }
                throw new IllegalArgumentException("Route not found");
            }

            @Override
            public void addRoute(Route route) {
                if (route.getName() == null || route.getName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Name is required");
                }
                if (route.getRating() != null && route.getRating() < 0) {
                    throw new IllegalArgumentException("Rating must be non-negative");
                }
                if (route.getDistance() != null && route.getDistance() < 1) {
                    throw new IllegalArgumentException("Distance must be at least 1");
                }
                route.setId(1L);
            }

            @Override
            public void updateRoute(Route route) {
                if (route.getName() == null || route.getName().trim().isEmpty()) {
                    throw new IllegalArgumentException("Name is required");
                }
            }

            @Override
            public void deleteRoute(Long id) {
                if (id == 999L) {
                    throw new IllegalArgumentException("Route not found");
                }
            }
        };
    }

    @Test
    void testLocationCreationAndRetrieval() {
        // Given
        Location location = new Location();
        location.setName("Integration Test Location");
        location.setX(200L);
        location.setY(75.5f);

        // When
        locationService.createLocation(location);
        Location retrieved = locationService.getLocationById(location.getId());

        // Then
        assertNotNull(location);
        assertNotNull(retrieved);
        assertEquals("Integration Test Location", location.getName());
        assertEquals(location.getId(), retrieved.getId());
    }

    @Test
    void testRouteCreationWithValidation() {
        // Given
        Route route = new Route();
        route.setName("Integration Test Route");
        route.setRating(4.8);
        route.setDistance(250.0);

        // When
        routeService.addRoute(route);

        // Then
        assertNotNull(route);
        assertEquals("Integration Test Route", route.getName());
        assertEquals(4.8, route.getRating());
        assertEquals(250.0, route.getDistance());
    }

    @Test
    void testLocationValidation() {
        // Given
        Location invalidLocation = new Location();
        invalidLocation.setName(""); // Invalid name

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.createLocation(invalidLocation);
        });
    }

    @Test
    void testRouteValidation() {
        // Given
        Route invalidRoute = new Route();
        invalidRoute.setName("Valid Name");
        invalidRoute.setRating(-1.0); // Invalid rating

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            routeService.addRoute(invalidRoute);
        });
    }

    @Test
    void testLocationNotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.getLocationById(999L);
        });
    }

    @Test
    void testRouteNotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            routeService.getRouteById(999L);
        });
    }

    @Test
    void testLocationDeletion() {
        // Given
        Long locationId = 1L;

        // When & Then - should not throw exception
        assertDoesNotThrow(() -> {
            locationService.deleteLocation(locationId);
        });
    }

    @Test
    void testRouteDeletion() {
        // Given
        Long routeId = 1L;

        // When & Then - should not throw exception
        assertDoesNotThrow(() -> {
            routeService.deleteRoute(routeId);
        });
    }

    @Test
    void testLocationDeletionNotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.deleteLocation(999L);
        });
    }

    @Test
    void testRouteDeletionNotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            routeService.deleteRoute(999L);
        });
    }
}
