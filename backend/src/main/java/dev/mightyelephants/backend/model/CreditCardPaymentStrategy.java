package dev.mightyelephants.backend.model;
import java.util.Map;
import org.springframework.stereotype.Component;
@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment createPayment(double amount, Map<String, String> paymentDetails) {
        CreditCardPayment payment = new CreditCardPayment();
        payment.setAmount(amount);
        payment.setCardNumber(paymentDetails.get("cardNumber"));
        payment.setCardHolderName(paymentDetails.get("cardHolderName"));
        payment.setCvv(paymentDetails.get("cvv"));
        payment.setExpiryDate(paymentDetails.get("expiryDate"));
        return payment;
    }
}
