package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDelivery(String customerName, String customerEmail, Long shippingLabelId){
        Delivery delivery = new Delivery(customerName, customerEmail, shippingLabelId);
        return deliveryRepository.save(delivery);
    }
}
