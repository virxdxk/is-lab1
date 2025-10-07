package itmo.is.controller;

import itmo.is.entities.Route;
import itmo.is.entities.Coordinates;
import itmo.is.entities.Location;
import itmo.is.service.RouteService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteControllerTest {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteController routeController;

    private Route testRoute;
    private Location fromLocation;
    private Location toLocation;
    private Coordinates coordinates;

    @BeforeEach
    void setUp() {
        // Create test coordinates
        coordinates = new Coordinates();
        coordinates.setX(10.5f);
        coordinates.setY(20.3);

        // Create test locations
        fromLocation = new Location();
        fromLocation.setId(1L);
        fromLocation.setName("From Location");
        fromLocation.setX(100L);
        fromLocation.setY(50.5f);

        toLocation = new Location();
        toLocation.setId(2L);
        toLocation.setName("To Location");
        toLocation.setX(200L);
        toLocation.setY(60.5f);

        // Create test route
        testRoute = new Route();
        testRoute.setId(1L);
        testRoute.setName("Test Route");
        testRoute.setCoordinates(coordinates);
        testRoute.setCreationDate(new Date());
        testRoute.setFrom(fromLocation);
        testRoute.setTo(toLocation);
        testRoute.setDistance(150.5);
        testRoute.setRating(4.5);
    }

    @Test
    void testGetAllRoutes() {
        // Given
        List<Route> routes = Arrays.asList(testRoute);
        when(routeService.getAllRoutes()).thenReturn(routes);

        // When
        List<Route> result = routeController.getAllRoutes();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Route", result.get(0).getName());
        verify(routeService).getAllRoutes();
    }

    @Test
    void testGetRouteById_Success() {
        // Given
        when(routeService.getRouteById(1L)).thenReturn(testRoute);

        // When
        Response response = routeController.getRouteById(1L);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testRoute, response.getEntity());
        verify(routeService).getRouteById(1L);
    }

    @Test
    void testGetRouteById_NotFound() {
        // Given
        when(routeService.getRouteById(999L))
                .thenThrow(new IllegalArgumentException("Route not found"));

        // When
        Response response = routeController.getRouteById(999L);

        // Then
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Route not found", response.getEntity());
        verify(routeService).getRouteById(999L);
    }

    @Test
    void testCreateRoute_Success() {
        // Given
        doNothing().when(routeService).addRoute(any(Route.class));

        // When
        Response response = routeController.createRoute(testRoute);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testRoute, response.getEntity());
        verify(routeService).addRoute(testRoute);
    }

    @Test
    void testCreateRoute_BadRequest() {
        // Given
        Route invalidRoute = new Route();
        invalidRoute.setName(""); // Invalid name
        doThrow(new IllegalArgumentException("Name is required"))
                .when(routeService).addRoute(any(Route.class));

        // When
        Response response = routeController.createRoute(invalidRoute);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Name is required", response.getEntity());
        verify(routeService).addRoute(invalidRoute);
    }

    @Test
    void testUpdateRoute_Success() {
        // Given
        doNothing().when(routeService).updateRoute(any(Route.class));

        // When
        Response response = routeController.updateRoute(1L, testRoute);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testRoute, response.getEntity());
        verify(routeService).updateRoute(testRoute);
    }

    @Test
    void testUpdateRoute_BadRequest() {
        // Given
        Route invalidRoute = new Route();
        invalidRoute.setName(""); // Invalid name
        doThrow(new IllegalArgumentException("Name is required"))
                .when(routeService).updateRoute(any(Route.class));

        // When
        Response response = routeController.updateRoute(1L, invalidRoute);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Name is required", response.getEntity());
        verify(routeService).updateRoute(invalidRoute);
    }

    @Test
    void testDeleteRoute_Success() {
        // Given
        doNothing().when(routeService).deleteRoute(1L);

        // When
        Response response = routeController.deleteRoute(1L);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(routeService).deleteRoute(1L);
    }

    @Test
    void testDeleteRoute_NotFound() {
        // Given
        doThrow(new IllegalArgumentException("Route not found"))
                .when(routeService).deleteRoute(999L);

        // When
        Response response = routeController.deleteRoute(999L);

        // Then
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Route not found", response.getEntity());
        verify(routeService).deleteRoute(999L);
    }

    @Test
    void testCreateRouteWithValidation() {
        // Given - create a route with invalid rating
        Route invalidRoute = new Route();
        invalidRoute.setName("Valid Name");
        invalidRoute.setRating(-1.0); // Invalid rating (should be >= 0)
        doThrow(new IllegalArgumentException("Rating must be non-negative"))
                .when(routeService).addRoute(any(Route.class));

        // When
        Response response = routeController.createRoute(invalidRoute);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Rating must be non-negative", response.getEntity());
        verify(routeService).addRoute(invalidRoute);
    }

    @Test
    void testCreateRouteWithInvalidDistance() {
        // Given - create a route with invalid distance
        Route invalidRoute = new Route();
        invalidRoute.setName("Valid Name");
        invalidRoute.setDistance(0.0); // Invalid distance (should be >= 1)
        doThrow(new IllegalArgumentException("Distance must be at least 1"))
                .when(routeService).addRoute(any(Route.class));

        // When
        Response response = routeController.createRoute(invalidRoute);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Distance must be at least 1", response.getEntity());
        verify(routeService).addRoute(invalidRoute);
    }
}
