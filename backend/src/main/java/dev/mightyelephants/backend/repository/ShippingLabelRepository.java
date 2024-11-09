package dev.mightyelephants.backend.repository;

import dev.mightyelephants.backend.model.ShippingLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingLabelRepository extends JpaRepository<ShippingLabel, Long> {
}