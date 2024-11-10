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

    @Min(value = 0, message = "length must be non-negative")
    private double length;

    @Min(value = 0, message = "Width must be non-negative")
    private double width;

    @Min(value = 0, message = "Height must be non-negative")
    private double height;



    public QuoteRequest(String origin, String destination, double weight, double length, double width, double height) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
