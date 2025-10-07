package itmo.is.controller;

import itmo.is.entities.Location;
import itmo.is.service.LocationService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private Location testLocation;

    @BeforeEach
    void setUp() {
        testLocation = new Location();
        testLocation.setId(1L);
        testLocation.setName("Test Location");
        testLocation.setX(100L);
        testLocation.setY(50.5f);
    }

    @Test
    void testGetAllLocations() {
        // Given
        List<Location> locations = Arrays.asList(testLocation);
        when(locationService.getAllLocations()).thenReturn(locations);

        // When
        List<Location> result = locationController.getAllLocations();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Location", result.get(0).getName());
        verify(locationService).getAllLocations();
    }

    @Test
    void testGetLocationById_Success() {
        // Given
        when(locationService.getLocationById(1L)).thenReturn(testLocation);

        // When
        Response response = locationController.getLocationById(1L);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testLocation, response.getEntity());
        verify(locationService).getLocationById(1L);
    }

    @Test
    void testGetLocationById_NotFound() {
        // Given
        when(locationService.getLocationById(999L))
                .thenThrow(new IllegalArgumentException("Location not found"));

        // When
        Response response = locationController.getLocationById(999L);

        // Then
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Location not found", response.getEntity());
        verify(locationService).getLocationById(999L);
    }

    @Test
    void testCreateLocation_Success() {
        // Given
        doNothing().when(locationService).createLocation(any(Location.class));

        // When
        Response response = locationController.createLocation(testLocation);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testLocation, response.getEntity());
        verify(locationService).createLocation(testLocation);
    }

    @Test
    void testCreateLocation_BadRequest() {
        // Given
        Location invalidLocation = new Location();
        invalidLocation.setName(""); // Invalid name
        doThrow(new IllegalArgumentException("Name is required"))
                .when(locationService).createLocation(any(Location.class));

        // When
        Response response = locationController.createLocation(invalidLocation);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Name is required", response.getEntity());
        verify(locationService).createLocation(invalidLocation);
    }

    @Test
    void testUpdateLocation_Success() {
        // Given
        doNothing().when(locationService).updateLocation(any(Location.class));

        // When
        Response response = locationController.updateLocation(1L, testLocation);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(testLocation, response.getEntity());
        verify(locationService).updateLocation(testLocation);
    }

    @Test
    void testUpdateLocation_BadRequest() {
        // Given
        Location invalidLocation = new Location();
        invalidLocation.setName(""); // Invalid name
        doThrow(new IllegalArgumentException("Name is required"))
                .when(locationService).updateLocation(any(Location.class));

        // When
        Response response = locationController.updateLocation(1L, invalidLocation);

        // Then
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertEquals("Name is required", response.getEntity());
        verify(locationService).updateLocation(invalidLocation);
    }

    @Test
    void testDeleteLocation_Success() {
        // Given
        doNothing().when(locationService).deleteLocation(1L);

        // When
        Response response = locationController.deleteLocation(1L);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(locationService).deleteLocation(1L);
    }

    @Test
    void testDeleteLocation_NotFound() {
        // Given
        doThrow(new IllegalArgumentException("Location not found"))
                .when(locationService).deleteLocation(999L);

        // When
        Response response = locationController.deleteLocation(999L);

        // Then
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals("Location not found", response.getEntity());
        verify(locationService).deleteLocation(999L);
    }
}
