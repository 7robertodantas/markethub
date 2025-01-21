package br.ufrn.imd.markethub.service.checkout.repository;

import br.ufrn.imd.markethub.service.checkout.domain.Checkout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckoutRepository extends JpaRepository<Checkout, UUID> {
    Page<Checkout> findByUserId(UUID userId, Pageable pageable);
}
