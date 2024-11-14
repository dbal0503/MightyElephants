package dev.mightyelephants.backend.model;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;
@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment createPayment(Quote quote, Map<String, String> paymentDetails) {
        CreditCardPayment payment = new CreditCardPayment();
        payment.setQuote(quote);
        payment.setCardNumber(paymentDetails.get("cardNumber"));
        payment.setCardHolderName(paymentDetails.get("cardHolderName"));
        payment.setCvv(paymentDetails.get("cvv"));
        payment.setExpiryMonth(paymentDetails.get("expiryMonth"));
        payment.setExpiryYear(paymentDetails.get("expiryYear"));
        return payment;
    }
}
