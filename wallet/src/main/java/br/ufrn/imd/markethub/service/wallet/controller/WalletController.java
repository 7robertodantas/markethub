package br.ufrn.imd.markethub.service.wallet.controller;

import br.ufrn.imd.markethub.service.wallet.dto.WalletBalanceDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletHistoryDto;
import br.ufrn.imd.markethub.service.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance/{userId}")
    public ResponseEntity<WalletBalanceDto> getBalance(@PathVariable UUID userId) {
        final WalletBalanceDto balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<Page<WalletHistoryDto>> getHistory(@PathVariable UUID userId, Pageable pageable) {
        final Page<WalletHistoryDto> history = walletService.getHistory(userId, pageable);
        return ResponseEntity.ok(history);
    }
}
