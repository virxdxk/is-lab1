package itmo.is.entities;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Coordinates coordinates;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private Location from;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private Location to;

    @Min(1)
    @Column(name = "distance", nullable = true)
    private Double distance;

    @Min(0)
    @Column(name = "rating", nullable = false)
    private Double rating;
}
