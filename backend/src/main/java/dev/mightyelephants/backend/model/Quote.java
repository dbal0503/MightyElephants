package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "quotes")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPriceForLabel() {
        return priceForLabel;
    }

    public void setPriceForLabel(double priceForLabel) {
        this.priceForLabel = priceForLabel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
