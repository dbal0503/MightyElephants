package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.ExpressShippingLabel;
import dev.mightyelephants.backend.model.StandardShippingLabel;
import dev.mightyelephants.backend.model.ShippingLabel;

public class ShippingLabelFactory {
    public static ShippingLabel createLabel(Quote quote) {
        if ("Express".equals(quote.getShippingType())) {
             return new ExpressShippingLabel(quote);
        } else if ("Standard".equals(quote.getShippingType())) {
                return new StandardShippingLabel(quote);
        } else {
            throw new IllegalArgumentException("Invalid shipping type: " + quote.getShippingType());
        }
    }
}
