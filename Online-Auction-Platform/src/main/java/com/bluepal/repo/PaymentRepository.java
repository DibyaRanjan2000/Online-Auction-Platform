package com.bluepal.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bluepal.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPurchaseId(Long purchaseId);
}
