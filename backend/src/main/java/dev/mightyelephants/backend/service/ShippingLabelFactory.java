package dev.mightyelephants.backend.service;
import dev.mightyelephants.backend.model.Quote;
import dev.mightyelephants.backend.model.Payment;
import dev.mightyelephants.backend.model.ExpressShippingLabel;
import dev.mightyelephants.backend.model.StandardShippingLabel;
import dev.mightyelephants.backend.model.ShippingLabel;

public class ShippingLabelFactory {
    public static ShippingLabel createLabel(Quote quote, Payment payment) {
        System.out.println(quote.getShippingType());
        if ("Express".equals(quote.getShippingType())) {
             return new ExpressShippingLabel(quote, payment);
        } else if ("Standard".equals(quote.getShippingType())) {
                return new StandardShippingLabel(quote, payment);
        } else {
            throw new IllegalArgumentException("Invalid shipping type: " + quote.getShippingType());
        }
    }
}
