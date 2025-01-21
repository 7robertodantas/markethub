package br.ufrn.imd.markethub.service.checkout.controller;

import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import br.ufrn.imd.markethub.service.checkout.exception.ServerException;
import br.ufrn.imd.markethub.service.checkout.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService service;

    @PostMapping
    public ResponseEntity<CheckoutDto> submitCheckout(@RequestBody CheckoutRequestDto checkoutRequest) {
        final CheckoutDto checkout = service.createCheckout(checkoutRequest);
        return ResponseEntity.ok(checkout);
    }

    @GetMapping("/{checkoutId}")
    public ResponseEntity<CheckoutDto> getCheckoutStatus(@PathVariable UUID checkoutId) {
        final Optional<CheckoutDto> checkout = service.findById(checkoutId);
        return checkout.map(ResponseEntity::ok).orElseThrow(ServerException::notFound);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<Page<CheckoutDto>> getCheckoutHistory(@PathVariable UUID userId, Pageable pageable) {
        final Page<CheckoutDto> checkouts = service.findByUserId(userId, pageable);
        return ResponseEntity.ok(checkouts);
    }
}