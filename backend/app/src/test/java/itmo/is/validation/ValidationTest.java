package itmo.is.validation;

import itmo.is.entities.Location;
import itmo.is.entities.Route;
import itmo.is.entities.Coordinates;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testLocationValidation_ValidLocation() {
        // Given
        Location location = new Location();
        location.setName("Valid Location");
        location.setX(100L);
        location.setY(50.5f);

        // When
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testLocationValidation_InvalidName() {
        // Given
        Location location = new Location();
        location.setName(""); // Invalid - blank name
        location.setX(100L);
        location.setY(50.5f);

        // When
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Name is required")));
    }

    @Test
    void testLocationValidation_NullName() {
        // Given
        Location location = new Location();
        location.setName(null); // Invalid - null name
        location.setX(100L);
        location.setY(50.5f);

        // When
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Name is required")));
    }

    @Test
    void testRouteValidation_ValidRoute() {
        // Given
        Route route = new Route();
        route.setName("Valid Route");
        route.setRating(4.5);
        route.setDistance(150.0);

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testRouteValidation_InvalidName() {
        // Given
        Route route = new Route();
        route.setName(""); // Invalid - blank name
        route.setRating(4.5);
        route.setDistance(150.0);

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Name is required")));
    }

    @Test
    void testRouteValidation_InvalidRating() {
        // Given
        Route route = new Route();
        route.setName("Valid Route");
        route.setRating(-1.0); // Invalid - negative rating
        route.setDistance(150.0);

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
    }

    @Test
    void testRouteValidation_InvalidDistance() {
        // Given
        Route route = new Route();
        route.setName("Valid Route");
        route.setRating(4.5);
        route.setDistance(0.5); // Invalid - distance less than 1

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("distance")));
    }

    @Test
    void testCoordinatesValidation_ValidCoordinates() {
        // Given
        Coordinates coordinates = new Coordinates();
        coordinates.setX(10.5f);
        coordinates.setY(25.0); // Valid - less than 49

        // When
        Set<ConstraintViolation<Coordinates>> violations = validator.validate(coordinates);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testCoordinatesValidation_InvalidY() {
        // Given
        Coordinates coordinates = new Coordinates();
        coordinates.setX(10.5f);
        coordinates.setY(50.0); // Invalid - Y must be less than 49

        // When
        Set<ConstraintViolation<Coordinates>> violations = validator.validate(coordinates);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Y must be less than 49")));
    }

    @Test
    void testRouteValidation_MultipleViolations() {
        // Given
        Route route = new Route();
        route.setName(""); // Invalid - blank name
        route.setRating(-1.0); // Invalid - negative rating
        route.setDistance(0.5); // Invalid - distance less than 1

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("distance")));
    }

    @Test
    void testLocationValidation_EdgeCaseValues() {
        // Given
        Location location = new Location();
        location.setName("A"); // Valid - single character
        location.setX(0L); // Valid - zero
        location.setY(0.0f); // Valid - zero

        // When
        Set<ConstraintViolation<Location>> violations = validator.validate(location);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testRouteValidation_EdgeCaseValues() {
        // Given
        Route route = new Route();
        route.setName("A"); // Valid - single character
        route.setRating(0.0); // Valid - zero rating
        route.setDistance(1.0); // Valid - minimum distance

        // When
        Set<ConstraintViolation<Route>> violations = validator.validate(route);

        // Then
        assertTrue(violations.isEmpty());
    }
}
