package dev.mightyelephants.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "quotes")
@Getter
@Setter
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String shippingType;

    private String estimatedDelivery;

    @Column(nullable = false)
    private double weight;


    public Quote() {
    }

    public Quote(String origin, String destination, LocalDate date, double price, String shippingType, double weight) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
        this.shippingType = shippingType;
        this.weight = weight;
    }

}
