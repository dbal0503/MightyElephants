package dev.mightyelephants.backend.model;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@DiscriminatorValue("PAYPAL")
public class PaypalPayment extends Payment {
    @Column(name = "email", nullable = false)
    private String email;

    @Override
    public boolean pay() {
        setStatus("COMPLETED");
        return true;
    }

}
