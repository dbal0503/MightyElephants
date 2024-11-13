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
    private String customerName;

    @Column(nullable = false)
    private String deliveryType;

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
    public Delivery(Long shippingLabelId, String customerName, String origin, String status, String deliveryType) {
        this.shippingLabelId = shippingLabelId;
        this.customerName = customerName;
        this.origin = origin;
        this.status = status;
        this.deliveryType = deliveryType;
    }
}
