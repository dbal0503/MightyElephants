package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public List<Quote> calculateShippingOptions(QuoteRequest quoteRequest) {
        List<Quote> options = new ArrayList<>();
        double weight = quoteRequest.getWeight();

        options.add(QuoteFactory.createStandardQuote(weight));
        options.add(QuoteFactory.createExpressQuote(weight));
        
        return options;
    }

    public Quote createQuote(Quote selectedQuote) {
        selectedQuote.setStatus("Pending");
        return quoteRepository.save(selectedQuote);
    }
}

