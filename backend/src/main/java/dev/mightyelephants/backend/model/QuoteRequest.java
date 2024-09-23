package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "quote_requests")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalAddress() {
        return originalAddress;
    }

    public void setOriginalAddress(String originalAddress) {
        this.originalAddress = originalAddress;
    }

    public String getPackageDimensions() {
        return packageDimensions;
    }

    public void setPackageDimensions(String packageDimensions) {
        this.packageDimensions = packageDimensions;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(double packageWeight) {
        this.packageWeight = packageWeight;
    }
}
