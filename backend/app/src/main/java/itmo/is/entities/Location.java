package itmo.is.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private Long x;

    @Column(name = "y")
    private Float y;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;
}
