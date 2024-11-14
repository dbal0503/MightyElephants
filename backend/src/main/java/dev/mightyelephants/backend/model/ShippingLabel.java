package dev.mightyelephants.backend.model;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "shipping_labels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class ShippingLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String shippingType;

    @Column(nullable = false)
    private String estimatedDelivery;

    @Column(nullable = false)
    private LocalDate dateIssued;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String recipient;

    public ShippingLabel() {
        this.dateIssued = LocalDate.now();
    }

    public ShippingLabel(String origin, String destination, double weight, String shippingType, String estimatedDelivery, String sender, String recipient, Quote quote, Payment payment) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.shippingType = shippingType;
        this.estimatedDelivery = estimatedDelivery;
        this.sender = sender;
        this.recipient = recipient;
        this.dateIssued = LocalDate.now();
        this.quote = quote;
        this.payment = payment;
    }

}
