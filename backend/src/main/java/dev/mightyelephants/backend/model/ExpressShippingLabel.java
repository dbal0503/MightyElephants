package dev.mightyelephants.backend.model;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@DiscriminatorValue("EXPRESS")
public class ExpressShippingLabel extends ShippingLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String trackingNumber = generateTrackingNumber();
    private  String origin;
    private String destination;
    private  double weight;
    private double price;
    private String estimatedDelivery="1-2 Business Days";
    private LocalDate dateIssued = LocalDate.now();

    public ExpressShippingLabel(Quote quote) {
        this.origin = quote.getOrigin();
        this.destination = quote.getDestination();
        this.weight = quote.getWeight();
        this.price = quote.getPrice();
    }

    public ExpressShippingLabel() {

    }

    private String generateTrackingNumber() {
        return "EXP-" + UUID.randomUUID().toString();
    }
}