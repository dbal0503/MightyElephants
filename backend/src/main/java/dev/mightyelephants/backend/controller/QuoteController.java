package dev.mightyelephants.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import dev.mightyelephants.backend.model.Quote;
import org.springframework.http.ResponseEntity;
import dev.mightyelephants.backend.service.QuoteService;
import org.springframework.web.bind.annotation.*;
import dev.mightyelephants.backend.service.QuoteRequest;
import java.util.List;
@RestController
@RequestMapping("/api/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/options")
    public ResponseEntity<List<Quote>> getShippingOptions(@RequestBody QuoteRequest quoteRequest) {
        List<Quote> options = quoteService.calculateShippingOptions(quoteRequest);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Quote> confirmQuote(@RequestBody Quote selectedQuote) {
        Quote confirmedQuote = quoteService.createQuote(selectedQuote);
        return ResponseEntity.ok(confirmedQuote);
    }
}