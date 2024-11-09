package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Quote;

public class QuoteFactory {
    private static final double STANDARD_MULTIPLIER = 1.5;
    private static final double EXPRESS_MULTIPLIER = 3.0;
    public static Quote createQuote(String shippingType, double weight,double multiplier, String estimatedDelivery) {
        double price = weight * multiplier;
        Quote quote = new Quote();
        quote.setShippingType(shippingType);
        quote.setPrice(price);
        quote.setEstimatedDelivery(estimatedDelivery);
        return quote;
    }

    public static Quote createStandardQuote(double weight) {
        return createQuote("Standard", weight, STANDARD_MULTIPLIER, "5-7 days");
    }

    public static Quote createExpressQuote(double weight) {
        return createQuote("Express", weight, EXPRESS_MULTIPLIER, "2-3 days");
    }
}
