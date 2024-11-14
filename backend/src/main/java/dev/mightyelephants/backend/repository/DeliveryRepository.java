package dev.mightyelephants.backend.repository;

import dev.mightyelephants.backend.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByStatus(String status);
    Optional<Delivery> findByShippingLabelId(Long shippingLabelId);
}
