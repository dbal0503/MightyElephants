package dev.mightyelephants.backend.controller;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery deliveryRequest){
        try {
            Delivery delivery = deliveryService.createDelivery(
                    deliveryRequest.getShippingLabelId(),
                    deliveryRequest.getSender(),
                    deliveryRequest.getOrigin(),
                    deliveryRequest.getDestination(),
                    deliveryRequest.getShippingType(),
                    deliveryRequest.getTrackingNumber()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/complete")
    public ResponseEntity<Void> completeDelivery(@RequestParam Long shippingLabelId){
        try {
            deliveryService.completeDelivery(shippingLabelId);
            return ResponseEntity.ok().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}














