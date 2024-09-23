package dev.mightyelephants.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicle_availabilities")
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

    public Long getTruckId() {
        return truckId;
    }

    public void setTruckId(Long truckId) {
        this.truckId = truckId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
