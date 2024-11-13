package dev.mightyelephants.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryDto {
    private Long shippingLabelId;
    private String customerName;
    private String origin;
    private String status;
    private String deliveryType;
}

