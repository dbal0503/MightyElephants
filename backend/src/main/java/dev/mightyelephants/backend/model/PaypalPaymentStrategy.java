package dev.mightyelephants.backend.model;
import org.springframework.stereotype.Component;
import java.util.Map;
import dev.mightyelephants.backend.model.Payment;
import dev.mightyelephants.backend.model.PaypalPayment;

@Component
public class PaypalPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment createPayment(Quote quote, Map<String, String> paymentDetails) {
        PaypalPayment payment = new PaypalPayment();
        payment.setQuote(quote);
        payment.setEmail(paymentDetails.get("email"));
        return payment;
    }
}
