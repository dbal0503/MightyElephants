package dev.mightyelephants.backend.model;

import jakarta.persistence.*;
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
    private double priceForLabel;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String shippingType;

    private String estimatedDelivery;

    @Column(nullable = false)
    private String status;


    public Quote() {

    }
    public Quote(double priceForLabel, LocalDate date, double price, String shippingType, String status) {
        this.priceForLabel = priceForLabel;
        this.date = date;
        this.price = price;
        this.shippingType = shippingType;
        this.status = status;
    }

}
