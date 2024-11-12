package dev.mightyelephants.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryDto {
    private String customerName;
    private String customerEmail;
    private Long shippingLabelId;
}
