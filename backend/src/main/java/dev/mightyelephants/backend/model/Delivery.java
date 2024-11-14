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

    //A BUNCH of this information will just be taken using shipping label

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long shippingLabelId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String shippingType;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false,  unique = true)
    private String trackingNumber;

    @Column(nullable = true)
    private Long deliveryManId; // Placeholder, we'll manage this later

    // Default constructor
    public Delivery() {}

    // Parameterized constructor
    public Delivery(Long shippingLabelId, String sender, String origin, String destination, String shippingType, String trackingNumber, String status) {
        this.shippingLabelId = shippingLabelId;
        this.sender = sender;
        this.origin = origin;
        this.destination = destination;
        this.shippingType = shippingType;
        this.trackingNumber = trackingNumber;
        this.status = status;
    }
}
