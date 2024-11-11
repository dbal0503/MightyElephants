package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.ShippingLabelInterface;
import dev.mightyelephants.backend.model.ExpressShippingLabel;
import dev.mightyelephants.backend.model.StandardShippingLabel;

public class ShippingLabelFactory {
    public static ShippingLabelInterface createLabel(Quote quote, QuoteRequest request) {
        if ("Express".equals(quote.getShippingType())) {
             return new ExpressShippingLabel(quote, request);
        } else if ("Standard".equals(quote.getShippingType())) {
                return new StandardShippingLabel(quote, request);
        } else {
            throw new IllegalArgumentException("Invalid shipping type: " + quote.getShippingType());
        }
    }
}
