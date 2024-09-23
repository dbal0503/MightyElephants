package dev.mightyelephants.backend.model;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "credit_card_payments")
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
