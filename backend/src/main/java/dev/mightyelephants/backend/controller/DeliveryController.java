package dev.mightyelephants.backend.controller;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.model.dto.DeliveryDto;
import dev.mightyelephants.backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDto deliveryRequest){
        try {
            Delivery delivery = deliveryService.createDelivery(
                    deliveryRequest.getCustomerName(),
                    deliveryRequest.getCustomerEmail(),
                    deliveryRequest.getShippingLabelId()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}














