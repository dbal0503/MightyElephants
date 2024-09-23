package dev.mightyelephants.backend.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDate date;

    public Payment() {
    }

    public Payment(double amount, String status, LocalDate date) {
        this.amount = amount;
        this.status = status;
        this.date = date;
    }
}
