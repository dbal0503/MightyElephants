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

    public Quote() {
    }

    public Quote(double priceForLabel, LocalDate date) {
        this.priceForLabel = priceForLabel;
        this.date = date;
    }
}
