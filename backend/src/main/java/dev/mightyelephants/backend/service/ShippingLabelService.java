package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Payment;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.ShippingLabel;
import dev.mightyelephants.backend.repository.ShippingLabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingLabelService {
    private final ShippingLabelRepository shippingLabelRepository;
    private QuoteService quoteService;
    private PaymentService paymentService;

    @Autowired
    public ShippingLabelService(ShippingLabelRepository shippingLabelRepository, QuoteService quoteService, PaymentService paymentService) {
        this.shippingLabelRepository = shippingLabelRepository;
        this.quoteService = quoteService;
        this.paymentService = paymentService;
    }

    public ShippingLabel generateShippingLabelAfterPayment(long quoteId, long paymentId) {

        Quote selectedQuote = quoteService.getQuoteById(quoteId)
                .orElseThrow(() -> new IllegalArgumentException("Quote not found with id: " + quoteId));

        Payment payment = paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + paymentId));

        ShippingLabel shippingLabel = (ShippingLabel) ShippingLabelFactory.createLabel(selectedQuote,payment);
        return shippingLabelRepository.save(shippingLabel); //Returns the full shipping label object
    }

}
