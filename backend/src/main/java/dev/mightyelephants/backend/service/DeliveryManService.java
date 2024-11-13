package dev.mightyelephants.backend.service;

import dev.mightyelephants.backend.model.DeliveryMan;
import dev.mightyelephants.backend.repository.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryManService {

    private final DeliveryManRepository deliveryManRepository;

    @Autowired
    public DeliveryManService(DeliveryManRepository deliveryManRepository) {
        this.deliveryManRepository = deliveryManRepository;
    }

    public Optional<DeliveryMan> getAvailableDeliveryMan(String originOfficeLocation, String driverShippingType, boolean isAvailable) {
        return deliveryManRepository.findFirstByOriginOfficeLocationAndDriverShippingTypeAndIsAvailable(
                originOfficeLocation, driverShippingType, isAvailable);
    }

    public void assignDelivery(DeliveryMan deliveryMan) {
        deliveryMan.setIsAvailable(false); // Mark as unavailable if needed
        deliveryMan.setNumberOfPackages(deliveryMan.getNumberOfPackages() + 1);
        deliveryManRepository.save(deliveryMan);
    }


}
