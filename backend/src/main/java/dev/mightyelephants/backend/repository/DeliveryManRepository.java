package dev.mightyelephants.backend.repository;

import dev.mightyelephants.backend.model.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long> {

    Optional<DeliveryMan> findFirstByOriginOfficeLocationAndDriverShippingTypeAndIsAvailable(
            String originOfficeLocation, String driverShippingType, boolean isAvailable);
}
