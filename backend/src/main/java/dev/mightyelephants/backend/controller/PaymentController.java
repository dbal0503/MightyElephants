package dev.mightyelephants.backend.controller;
import dev.mightyelephants.backend.model.Payment;
//import dev.mightyelephants.backend.model.QuoteRequest;
import dev.mightyelephants.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /*@PostMapping("/process")
    public ResponseEntity<Payment> processPayment(@RequestBody QuoteRequest quoteRequest) {
        try {
            Payment payment = paymentService.processPayment(
                    quoteRequest.getPaymentType(),
                    quoteRequest.getAmount(),
                    quoteRequest.getPaymentDetails()
            );

            return payment.getStatus().equals("COMPLETED")
                    ? ResponseEntity.ok(payment)
                    : ResponseEntity.badRequest().body(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}