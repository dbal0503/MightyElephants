package dev.mightyelephants.backend.controller;

import dev.mightyelephants.backend.service.ShippingLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import dev.mightyelephants.backend.model.ShippingLabel;
import java.util.HashMap;

@RestController
@RequestMapping("/api/shippinglabel")
public class ShippingLabelController {
    private final ShippingLabelService shippingLabelService;

    @Autowired
    public ShippingLabelController(ShippingLabelService shippingLabelService) {
        this.shippingLabelService = shippingLabelService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> createShippingLabel(@RequestBody Map<String,Object> shippingData) {
        try {
            Long paymentId = Long.valueOf(shippingData.get("paymentId").toString());
            Long quoteId = Long.valueOf(shippingData.get("quoteId").toString());
            System.out.println("Payment ID: " + paymentId);
            System.out.println("Quote ID: " + quoteId);

            if (paymentId == null || quoteId == null) {
                return ResponseEntity.badRequest().body(null);
            }

            ShippingLabel shippingLabel = shippingLabelService.generateShippingLabelAfterPayment(quoteId, paymentId);

            if (shippingLabel == null) {
                return ResponseEntity.badRequest().body(null);
            }

            Map<String, Object> shippingDetails = new HashMap<>();
            shippingDetails.put("shippingLabelId", shippingLabel.getId());
            shippingDetails.put("shippingType", shippingLabel.getShippingType());
            shippingDetails.put("weight", shippingLabel.getWeight());
            shippingDetails.put("origin", shippingLabel.getOrigin());
            shippingDetails.put("sender", shippingLabel.getSender());
            shippingDetails.put("recipient", shippingLabel.getRecipient());
            shippingDetails.put("destination", shippingLabel.getDestination());

            return ResponseEntity.ok(shippingDetails);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



}
