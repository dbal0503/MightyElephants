package dev.mightyelephants.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.ShippingLabel;
import dev.mightyelephants.backend.model.Payment;
import org.springframework.http.ResponseEntity;
import dev.mightyelephants.backend.service.QuoteService;
import dev.mightyelephants.backend.service.PaymentService;
import org.springframework.web.bind.annotation.*;
import dev.mightyelephants.backend.service.QuoteRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/quote")
public class QuoteController {
    private final QuoteService quoteService;
    private final PaymentService paymentService;

    @Autowired
    public QuoteController(QuoteService quoteService, PaymentService paymentService) {
        this.quoteService = quoteService;
        this.paymentService = paymentService;
    }
    @CrossOrigin(origins = "http://localhost:4200/quote-request")
    @PostMapping("/options")
    public ResponseEntity<List<Quote>> getShippingOptions(@RequestBody QuoteRequest quoteRequest) {
        System.out.println("Received quote request: " + quoteRequest);
        List<Quote> options = quoteService.calculateShippingOptions(quoteRequest);
        System.out.println("Shipping options generated: " + options);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/select/pay")
    public ResponseEntity<ShippingLabel> selectQuoteAndPay(
            @RequestParam String shippingType,
            @RequestParam double price,
            @RequestParam String estimatedDelivery,
            @RequestParam String paymentType,
            @RequestParam double amount,
            @RequestParam Map<String, String> paymentDetails,
            @RequestBody QuoteRequest quoteRequest) {

        try {
            // Create a Quote object for the selected quote based on the received details
            Quote selectedQuote = new Quote();
            selectedQuote.setShippingType(shippingType);
            selectedQuote.setPrice(price);
            selectedQuote.setEstimatedDelivery(estimatedDelivery);

            // Process the payment using the provided details
            Payment payment = paymentService.processPayment(paymentType, amount, paymentDetails);

            if (payment.getStatus().equals("COMPLETED")) {
                // Payment successful, now generate the shipping label
                ShippingLabel shippingLabel = quoteService.generateShippingLabelAfterPayment(quoteRequest, selectedQuote);
                return ResponseEntity.ok(shippingLabel);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}