package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.model.DeliveryMan;
import dev.mightyelephants.backend.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryQueueService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryManService deliveryManService;

    @Autowired
    public DeliveryQueueService(DeliveryRepository deliveryRepository, DeliveryManService deliveryManService) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManService = deliveryManService;
    }

    // Runs every minute to check for queued deliveries
    @Scheduled(fixedRate = 60000)
    public void assignQueuedDeliveries() {
        List<Delivery> queuedDeliveries = deliveryRepository.findByStatus("QUEUED");

        for (Delivery delivery : queuedDeliveries) {
            Optional<DeliveryMan> availableDeliveryMan = deliveryManService.getAvailableDeliveryMan(
                    delivery.getOrigin(), delivery.getShippingType(), true);

            //would have to check that number of packages < max package capacity
            if (availableDeliveryMan.isPresent()) {
                DeliveryMan deliveryMan = availableDeliveryMan.get();
                delivery.setDeliveryManId(deliveryMan.getId());
                delivery.setStatus("IN_PROGRESS");

                // Save updated delivery
                deliveryRepository.save(delivery);

                // Update DeliveryMan availability or other necessary fields
                deliveryManService.assignDelivery(deliveryMan);
            }
        }
    }
}
