package itmo.is.entities;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;


@Entity
@Getter @Setter
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    @Column(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    @Column(name = "from", nullable = true)
    private Location from;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    @Column(name = "to", nullable = true)
    private Location to;

    @Min(1)
    @Column(name = "distance", nullable = true)
    private Double distance;

    @Min(0)
    @Column(name = "rating", nullable = false)
    private Double rating;
}
