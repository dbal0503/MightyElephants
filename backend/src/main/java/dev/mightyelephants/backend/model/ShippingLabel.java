package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "shipping_labels")
@Getter
@Setter
public class ShippingLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String shippingType;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String estimatedDelivery;

    @Column(nullable = false)
    private LocalDate dateIssued;

    public ShippingLabel() {
        this.dateIssued = LocalDate.now();
    }

    public ShippingLabel(String origin, String destination, double weight, String shippingType, double price, String estimatedDelivery) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.shippingType = shippingType;
        this.price = price;
        this.estimatedDelivery = estimatedDelivery;
        this.dateIssued = LocalDate.now();
    }
}