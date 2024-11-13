package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dev.mightyelephants.backend.repository.ShippingLabelRepository;
import dev.mightyelephants.backend.model.ShippingLabel;

import java.time.LocalDate;
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

    public Quote saveQuote(Quote quoteRequest){
        Quote quote = new Quote();
        quote.setOrigin(quoteRequest.getOrigin());
        quote.setOriginLat(quoteRequest.getOriginLat());
        quote.setOriginLong(quoteRequest.getOriginLong());
        quote.setDestination(quoteRequest.getDestination());
        quote.setDestinationLat(quoteRequest.getDestinationLat());
        quote.setDestinationLong(quoteRequest.getDestinationLong());
        quote.setDate(LocalDate.now());
        quote.setPrice(quoteRequest.getPrice());
        quote.setShippingType(quoteRequest.getShippingType());
        quote.setWeight(quoteRequest.getWeight());
        quote.setEstimatedDelivery(quoteRequest.getEstimatedDelivery());
        return quoteRepository.save(quote);
    }


    public List<Quote> calculateShippingOptions(QuoteRequest quoteRequest) {
        List<Quote> options = new ArrayList<>();
        double weight = quoteRequest.getWeight();

        options.add(QuoteFactory.createStandardQuote(weight));
        options.add(QuoteFactory.createExpressQuote(weight));

        return options;
    }

    public ShippingLabel generateShippingLabelAfterPayment(Quote selectedQuote) {
        ShippingLabel shippingLabel = (ShippingLabel) ShippingLabelFactory.createLabel(selectedQuote);
        return shippingLabelRepository.save(shippingLabel);
    }
}

