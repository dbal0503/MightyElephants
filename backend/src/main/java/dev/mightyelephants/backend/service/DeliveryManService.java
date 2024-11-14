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

    public Optional<DeliveryMan> getAvailableDeliveryMan(String origin, String driverShippingType, boolean available) {
        return deliveryManRepository.findFirstByOriginAndDriverShippingTypeAndAvailable(
                origin, driverShippingType, true);
    }

//    Make it not available after every delivery, delivers right away
    public void assignDelivery(DeliveryMan deliveryMan) {
        deliveryMan.setAvailable(false); // Mark as unavailable if needed
        deliveryMan.setNumberOfPackages(deliveryMan.getNumberOfPackages() + 1);
        deliveryManRepository.save(deliveryMan);
    }

    //delivers once capacity filled (allows more packages, going to have to switch to this, but probably
    //going to have to put a scheduling method so that it
//    public void assignDelivery(DeliveryMan deliveryMan) {
//        if (deliveryMan.getNumberOfPackages() < deliveryMan.getPackageCapacity()) {
//            deliveryMan.setNumberOfPackages(deliveryMan.getNumberOfPackages() + 1);
//
//            if (deliveryMan.getNumberOfPackages() == deliveryMan.getPackageCapacity()) {
//                deliveryMan.setIsAvailable(false);
//            }
//
//            deliveryManRepository.save(deliveryMan);
//        } else {
//            throw new RuntimeException("DeliveryMan has reached maximum package capacity.");
//        }
//    }


}
