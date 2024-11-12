package dev.mightyelephants.backend.model;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue("STANDARD")
public class StandardShippingLabel extends ShippingLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String trackingNumber = generateTrackingNumber();
    private  String origin;
    private String destination;
    private  double weight;
    private double price;
    private String estimatedDelivery="3-5 Business Days";
    private LocalDate dateIssued = LocalDate.now();

    public StandardShippingLabel(Quote quote) {
        this.origin = quote.getOrigin();
        this.destination = quote.getDestination();
        this.weight = quote.getWeight();
        this.price = quote.getPrice();
    }

    public StandardShippingLabel() {

    }

    private String generateTrackingNumber() {
        return "STD-" + UUID.randomUUID().toString();
    }
}