package br.ufrn.imd.markethub.service.checkout.service;

import br.ufrn.imd.markethub.service.checkout.domain.Checkout;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CheckoutService {
    Checkout createCheckout(CheckoutRequestDto dto);
    Optional<Checkout> findById(UUID checkoutId);
    Page<Checkout> findByUserId(UUID userId, Pageable pageable);
}
