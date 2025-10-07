package itmo.is.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;

@Embeddable
public class Coordinates {
    private float x;

    @Max(value = 49, message = "Y must be less than 49")
    private double y;
}
