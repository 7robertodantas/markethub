package br.ufrn.imd.markethub.service.checkout.service;

import br.ufrn.imd.markethub.service.checkout.domain.Checkout;
import br.ufrn.imd.markethub.service.checkout.domain.CheckoutStatus;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import br.ufrn.imd.markethub.service.checkout.exception.ServerException;
import br.ufrn.imd.markethub.service.checkout.producer.CheckoutPublisher;
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

    private final CheckoutPublisher publisher;
    private final CheckoutRepository repository;

    @Override
    public CheckoutDto createCheckout(CheckoutRequestDto dto) {
        final Checkout checkout = new Checkout();
        checkout.setUserId(dto.getUserId());
        checkout.setStatus(CheckoutStatus.SUBMITTED);
        final Checkout saved = repository.save(checkout);
        final CheckoutDto checkoutDto = toDto(saved);
        publisher.sendCheckoutSubmitted(checkoutDto);
        return checkoutDto;
    }

    @Override
    public CheckoutDto done(UUID checkoutId) {
        final CheckoutDto result = updateStatus(checkoutId, CheckoutStatus.DONE);
        publisher.sendCheckoutDone(result);
        return result;
    }

    @Override
    public CheckoutDto fail(UUID checkoutId, String reason) {
        final CheckoutDto result = updateStatus(checkoutId, CheckoutStatus.FAILED);
        publisher.sendCheckoutFailed(result);
        return result;
    }

    @Override
    public Optional<CheckoutDto> findById(UUID checkoutId) {
        return repository.findById(checkoutId).map(CheckoutServiceImpl::toDto);
    }

    @Override
    public Page<CheckoutDto> findByUserId(UUID userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable).map(CheckoutServiceImpl::toDto);
    }

    private CheckoutDto updateStatus(UUID checkoutId, CheckoutStatus status) {
        final Checkout checkout = repository.findById(checkoutId).orElseThrow(ServerException::notFound);
        checkout.setStatus(status);
        final Checkout saved = repository.save(checkout);
        return toDto(saved);
    }

    private static CheckoutDto toDto(Checkout checkout) {
        return CheckoutDto.builder()
                .id(checkout.getId())
                .userId(checkout.getUserId())
                .status(checkout.getStatus())
                .createdAt(checkout.getCreatedAt())
                .build();
    }
}
