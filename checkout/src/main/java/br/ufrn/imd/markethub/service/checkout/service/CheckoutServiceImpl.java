package br.ufrn.imd.markethub.service.checkout.service;

import br.ufrn.imd.markethub.service.checkout.domain.Checkout;
import br.ufrn.imd.markethub.service.checkout.domain.CheckoutStatus;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import br.ufrn.imd.markethub.service.checkout.repository.CheckoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository repository;

    @Override
    public Checkout createCheckout(CheckoutRequestDto dto) {
        final Checkout checkout = new Checkout();
        checkout.setUserId(dto.getUserId());
        checkout.setStatus(CheckoutStatus.PENDING);
        return repository.save(checkout);
    }

    @Override
    public Optional<Checkout> findById(UUID checkoutId) {
        return repository.findById(checkoutId);
    }

    @Override
    public Page<Checkout> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }
}
