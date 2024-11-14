package dev.mightyelephants.backend.controller;
import dev.mightyelephants.backend.model.Payment;
//import dev.mightyelephants.backend.model.QuoteRequest;
import dev.mightyelephants.backend.model.ShippingLabel;
import dev.mightyelephants.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Long> processPayment(@RequestBody Map<String, Object> paymentData) {
        try{
            String paymentType = paymentData.get("paymentMethod").toString();
            Long quoteId = Long.valueOf(paymentData.get("quoteId").toString());
            Map<String, String> details = (Map<String, String>) paymentData.get("details");
            //System.out.println("Payment type: " + paymentType);
            //System.out.println("Quote ID: " + quoteId);
            //System.out.println("Details: " + details);

            if (details == null) {
                return ResponseEntity.badRequest().body(null); // Missing details or email
            }

            Payment payment = paymentService.processPayment(
                    paymentType,
                    quoteId,
                    details
            );
            //System.out.println(payment.getStatus());
            //System.out.println(payment.getId());
            return payment.getStatus().equals("COMPLETED")
                    ? ResponseEntity.ok(payment.getId())
                    : ResponseEntity.badRequest().body(null);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(1000L);
        }
    }

}