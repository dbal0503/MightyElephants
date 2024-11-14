package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.Delivery;
import dev.mightyelephants.backend.model.DeliveryMan;
import dev.mightyelephants.backend.repository.DeliveryManRepository;
import dev.mightyelephants.backend.repository.DeliveryRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryManService deliveryManService;
    private final DeliveryManRepository deliveryManRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, DeliveryManService deliveryManService, DeliveryManRepository deliveryManRepository) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryManService = deliveryManService;
        this.deliveryManRepository = deliveryManRepository;
    }

    public Delivery createDelivery(Long shippingLabelId, String sender, String origin, String destination, String shippingType) {

        Optional<DeliveryMan> availableDeliveryMan = deliveryManService.getAvailableDeliveryMan(origin, shippingType, true);

        String status = availableDeliveryMan.isPresent() ? "IN_PROGRESS" : "QUEUED";
        Long deliveryManId = availableDeliveryMan.map(DeliveryMan::getId).orElse(null);

        Delivery delivery = new Delivery(shippingLabelId, sender, origin, destination, status, shippingType);
        delivery.setDeliveryManId(deliveryManId);

        availableDeliveryMan.ifPresent(deliveryManService::assignDelivery);

        return deliveryRepository.save(delivery);
    }

    // we check number pf packages for delivery mana
//    public Delivery createDelivery(String customerName, String customerEmail, Long shippingLabelId,
//                                   LocalDateTime estimatedDeliveryTime, String origin, String driverShippingType) {
//
//        Optional<DeliveryMan> availableDeliveryMan = deliveryManService.getAvailableDeliveryMan(origin, driverShippingType, true);
//
//        if (availableDeliveryMan.isPresent()) {
//            DeliveryMan deliveryMan = availableDeliveryMan.get();
//
//            // Check and increment number of packages
//            deliveryManService.assignDelivery(deliveryMan);
//
//            Delivery delivery = new Delivery(customerName, customerEmail, shippingLabelId, estimatedDeliveryTime, "IN_PROGRESS");
//            delivery.setDeliveryManId(deliveryMan.getId());
//
//            return deliveryRepository.save(delivery);
//        } else {
//            // No delivery man available, queue delivery
//            Delivery delivery = new Delivery(customerName, customerEmail, shippingLabelId, estimatedDeliveryTime, "QUEUED");
//            return deliveryRepository.save(delivery);
//        }
//    }


    public void completeDelivery(long shippingLabelId){
        Delivery delivery = deliveryRepository.findByShippingLabelId(shippingLabelId)
                .orElseThrow(() -> new RuntimeException("Delivery not found for Shipping Label Id: " + shippingLabelId));

        delivery.setStatus("COMPLETED");

        if (delivery.getDeliveryManId() != null) {
          DeliveryMan deliveryMan = deliveryManRepository.findById(delivery.getDeliveryManId())
                  .orElseThrow(() -> new RuntimeException(("DeliveryMan not found for ID: " + delivery.getDeliveryManId())));

          deliveryMan.setAvailable(true);
          deliveryMan.setNumberOfPackages(Math.max(deliveryMan.getNumberOfPackages() -1, 0));
          deliveryManRepository.save(deliveryMan);
        }

        deliveryRepository.save(delivery);

    }




}












