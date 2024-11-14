package dev.mightyelephants.backend.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("CREDIT_CARD")
public class CreditCardPayment extends Payment {
    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiry_month", nullable = false)
    private String expiryMonth;

    @Column(name = "expiry_year", nullable = false)
    private String expiryYear;

    @Override
    public boolean pay() {
        setStatus("COMPLETED");
        return true;
    }
}
