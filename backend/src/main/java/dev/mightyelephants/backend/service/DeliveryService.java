package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.model.DeliveryMan;
import dev.mightyelephants.backend.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryManService deliveryManService;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryManService deliveryManService) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManService = deliveryManService;
    }

    public Delivery createDelivery(String customerName, String customerEmail, Long shippingLabelId,
                                   LocalDateTime estimatedDeliveryTime, String origin, String driverShippingType) {

        Optional<DeliveryMan> availableDeliveryMan = deliveryManService.getAvailableDeliveryMan(origin, driverShippingType, true);

        String status = availableDeliveryMan.isPresent() ? "IN_PROGRESS" : "QUEUED";
        Long deliveryManId = availableDeliveryMan.map(DeliveryMan::getId).orElse(null);

        Delivery delivery = new Delivery(customerName, shippingLabelId, status, origin);
        delivery.setDeliveryManId(deliveryManId);

        return deliveryRepository.save(delivery);
    }





}
