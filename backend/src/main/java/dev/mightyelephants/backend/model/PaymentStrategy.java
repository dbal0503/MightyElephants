package dev.mightyelephants.backend.model;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
public interface PaymentStrategy {
    Payment createPayment(double amount, Map<String, String> paymentDetails);
}
