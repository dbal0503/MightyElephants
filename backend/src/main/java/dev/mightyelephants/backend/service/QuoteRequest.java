package dev.mightyelephants.backend.service;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteRequest {
    @NotNull(message = "Origin is required")
    private String origin;

    @NotNull(message = "Destination is required")
    private String destination;

    @Min(value = 0, message = "Weight must be non-negative")
    private double weight;

    @NotNull(message = "Dimensions are required")
    private String dimensions;

    @NotNull(message = "Shipping type is required")
    private String shippingType;

    public QuoteRequest(String origin, String destination, double weight, String dimensions, String shippingType) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.dimensions = dimensions;
        this.shippingType = shippingType;
    }
}
