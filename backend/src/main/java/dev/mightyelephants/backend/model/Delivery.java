package dev.mightyelephants.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Long shippingLabelId;

    @Column(nullable = true)
    private Long deliveryManId; // Placeholder, we'll manage this later

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String status;

    // Default constructor
    public Delivery() {}

    // Parameterized constructor
    public Delivery(String customerName, Long shippingLabelId, String status, String origin) {
        this.customerName = customerName;
        this.shippingLabelId = shippingLabelId;
        this.status = status;
        this.origin = origin;
    }
}
