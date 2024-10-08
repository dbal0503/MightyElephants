package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "quote_requests")
@Getter
@Setter
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalAddress;

    @Column(nullable = false)
    private String packageDimensions;

    @Column(nullable = false)
    private String destinationAddress;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double packageWeight;

    public QuoteRequest() {
    }

    public QuoteRequest(String originalAddress, String packageDimensions, String destinationAddress, LocalDate date, double packageWeight) {
        this.originalAddress = originalAddress;
        this.packageDimensions = packageDimensions;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.packageWeight = packageWeight;
    }
}
