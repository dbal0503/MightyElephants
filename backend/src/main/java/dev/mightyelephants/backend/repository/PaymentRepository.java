package dev.mightyelephants.backend.repository;
import java.util.List;
import dev.mightyelephants.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatus(String status);
    List<Payment> findByPaymentMethodType(String paymentMethodType);
}

