package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.mightyelephants.backend.repository.ShippingLabelRepository;
import dev.mightyelephants.backend.service.QuoteRequest;
import dev.mightyelephants.backend.model.ShippingLabel;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final ShippingLabelRepository shippingLabelRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository, ShippingLabelRepository shippingLabelRepository) {
        this.quoteRepository = quoteRepository;
        this.shippingLabelRepository = shippingLabelRepository;
    }

    public List<Quote> calculateShippingOptions(QuoteRequest quoteRequest) {
        List<Quote> options = new ArrayList<>();
        double weight = quoteRequest.getWeight();

        options.add(QuoteFactory.createStandardQuote(weight));
        options.add(QuoteFactory.createExpressQuote(weight));

        return options;
    }

    public ShippingLabel generateShippingLabelAfterPayment(QuoteRequest quoteRequest, Quote selectedQuote) {
        ShippingLabel shippingLabel = new ShippingLabel();
        shippingLabel.setShippingType(selectedQuote.getShippingType());
        shippingLabel.setPrice(selectedQuote.getPrice());
        shippingLabel.setOrigin(quoteRequest.getOrigin());
        shippingLabel.setDestination(quoteRequest.getDestination());
        shippingLabel.setWeight(quoteRequest.getWeight());
        shippingLabel.setEstimatedDelivery(selectedQuote.getEstimatedDelivery());

        return shippingLabelRepository.save(shippingLabel);
    }
}

