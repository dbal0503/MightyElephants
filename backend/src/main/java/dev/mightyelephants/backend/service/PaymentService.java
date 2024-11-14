package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Payment;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.PaymentStrategy;
import dev.mightyelephants.backend.repository.PaymentRepository;
import dev.mightyelephants.backend.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final Map<String, PaymentStrategy> paymentStrategies;

    private QuoteService quoteService;

    @Autowired
    public PaymentService( PaymentRepository paymentRepository, QuoteService quoteService, List<PaymentStrategy> strategies) {
        this.paymentRepository = paymentRepository;
        this.quoteService = quoteService;

        // Map of the payment strategies
        this.paymentStrategies = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getClass().getSimpleName(),
                        Function.identity()
                ));
    }

    @Transactional
    public Payment processPayment(String paymentType, long quoteId, Map<String, String> paymentDetails) {
        try {
            //System.out.println("Processing payment");
            // Pick right strategy to use based on payment type
            Quote quote = quoteService.getQuoteById(quoteId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found with id: " + quoteId));
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
            Payment payment = strategy.createPayment(quote, paymentDetails);

            // Process payment
            boolean paymentSuccessful = payment.pay();

            if (paymentSuccessful) {
                return paymentRepository.save(payment);
            } else {
                payment.setStatus("FAILED");
                return paymentRepository.save(payment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing payment: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing payment");
        }
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }
}
