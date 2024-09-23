package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicle_availabilities")
@Getter
@Setter
public class VehicleAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long truckId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private boolean isAvailable;

    public VehicleAvailability() {
    }

    public VehicleAvailability(String location, boolean isAvailable) {
        this.location = location;
        this.isAvailable = isAvailable;
    }
}
