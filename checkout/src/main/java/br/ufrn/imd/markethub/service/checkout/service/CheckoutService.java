package br.ufrn.imd.markethub.service.checkout.service;

import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CheckoutService {
    CheckoutDto createCheckout(CheckoutRequestDto dto);
    Optional<CheckoutDto> findById(UUID checkoutId);
    Page<CheckoutDto> findByUserId(UUID userId, Pageable pageable);
}
