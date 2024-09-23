package dev.mightyelephants.backend.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_card_payments")
@Getter
@Setter
public class CreditCardPayment extends Payment {

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false)
    private String cardholderName;

    @Column(nullable = false)
    private String billingAddress;

    @Column(nullable = false)
    private String phoneNumber;

    public CreditCardPayment() {
    }

    public CreditCardPayment(double amount, String status, LocalDate date, String cardNumber, String expirationDate, String cvv, String cardholderName, String billingAddress, String phoneNumber) {
        super(amount, status, date);
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.cardholderName = cardholderName;
        this.billingAddress = billingAddress;
        this.phoneNumber = phoneNumber;
    }
}
