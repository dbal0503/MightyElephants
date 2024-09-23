package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "shipping_labels")
@Getter
@Setter
public class ShippingLabel {

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

    @Column(nullable = false, unique = true)
    private String trackingNumber;

    @Column(nullable = false)
    private String returnAddress;

    @Column(nullable = false)
    private String phoneNumber;

    public ShippingLabel() {
    }

    public ShippingLabel(String originalAddress, String packageDimensions, String destinationAddress, LocalDate date, String trackingNumber, String returnAddress, String phoneNumber) {
        this.originalAddress = originalAddress;
        this.packageDimensions = packageDimensions;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.trackingNumber = trackingNumber;
        this.returnAddress = returnAddress;
        this.phoneNumber = phoneNumber;
    }
}
