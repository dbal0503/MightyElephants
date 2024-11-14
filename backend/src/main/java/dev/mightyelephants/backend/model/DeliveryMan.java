package dev.mightyelephants.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_men")
@Getter
@Setter
public class DeliveryMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private int numberOfPackages;

    @Column(nullable = false)
    private int packageCapacity;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String driverShippingType; // Can be "Express" or "Standard"

    @Column(nullable = false)
    private boolean available;

    // Default constructor
    public DeliveryMan() {}

    // Parameterized constructor
    public DeliveryMan(Long vehicleId, String name, int packageCapacity, String driverShippingType) {
        this.vehicleId = vehicleId;
        this.name = name;
        this.packageCapacity = packageCapacity;
        this.driverShippingType = driverShippingType;
        this.available = true;
        this.numberOfPackages = 0; // Initialize with zero packages
    }
}
