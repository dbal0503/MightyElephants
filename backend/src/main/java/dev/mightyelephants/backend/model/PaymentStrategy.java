package dev.mightyelephants.backend.model;
import java.util.Map;
public interface PaymentStrategy {
    Payment createPayment(double amount, Map<String, String> paymentDetails);
}
