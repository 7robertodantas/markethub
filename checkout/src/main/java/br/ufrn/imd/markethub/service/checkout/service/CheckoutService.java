package br.ufrn.imd.markethub.service.checkout.service;

import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CheckoutService {

    // checkout retrieve
    Optional<CheckoutDto> findById(UUID checkoutId);
    Page<CheckoutDto> findByUserId(UUID userId, Pageable pageable);

    // checkout management
    CheckoutDto createCheckout(CheckoutRequestDto dto);
    CheckoutDto done(UUID checkoutId);
    CheckoutDto fail(UUID checkoutId, String reason);

}
