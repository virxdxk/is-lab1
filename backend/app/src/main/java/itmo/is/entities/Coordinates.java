package itmo.is.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;

@Entity
@Table(name = "coordinates")
public class Coordinates {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private Float x;

    @Max(49)
    @Column(name = "y")
    private Double y;
}
