package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Payment;
import dev.mightyelephants.backend.model.PaymentStrategy;
import dev.mightyelephants.backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final Map<String, PaymentStrategy> paymentStrategies;

    @Autowired
    public PaymentService( PaymentRepository paymentRepository, List<PaymentStrategy> strategies) {
        this.paymentRepository = paymentRepository;

        // Map of the payment strategies
        this.paymentStrategies = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getClass().getSimpleName(),
                        Function.identity()
                ));
    }

    @Transactional
    public Payment processPayment(String paymentType, double amount, Map<String, String> paymentDetails) {
        // Pick right strategy to use based on payment type
        String strategyName = null;
        if (paymentType.equals("CREDIT_CARD")) {
            strategyName = "CreditCardPaymentStrategy";
        } else if (paymentType.equals("PAYPAL")) {
            strategyName = "PaypalPaymentStrategy";
        } else {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }


        PaymentStrategy strategy = paymentStrategies.get(strategyName);

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }

        // Create payment
        Payment payment = strategy.createPayment(amount, paymentDetails);

        // Process payment
        boolean paymentSuccessful = payment.pay();

        if (paymentSuccessful) {
            return paymentRepository.save(payment);
        } else {
            payment.setStatus("FAILED");
            return paymentRepository.save(payment);
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }
}
