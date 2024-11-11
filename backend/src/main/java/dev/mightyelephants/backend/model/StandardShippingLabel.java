package dev.mightyelephants.backend.model;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.mightyelephants.backend.service.QuoteRequest;

import java.time.LocalDate;

@Entity
@Table(name = "expressshipping_labels")
@Getter
@Setter
public class StandardShippingLabel implements ShippingLabelInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String trackingNumber = UUID.randomUUID().toString();
    private  String origin;
    private String destination;
    private  double weight;
    private double price;
    private String estimatedDelivery;
    private LocalDate dateIssued = LocalDate.now();

    public StandardShippingLabel(Quote quote, QuoteRequest request) {
        this.origin = request.getOrigin();
        this.destination = request.getDestination();
        this.weight = quote.getWeight();
        this.price = quote.getPrice();
        this.estimatedDelivery = quote.getEstimatedDelivery();
    }

    public StandardShippingLabel() {

    }
}