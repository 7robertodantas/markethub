package br.ufrn.imd.markethub.service.wallet.controller;

import br.ufrn.imd.markethub.service.wallet.dto.WalletBalanceDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletHistoryDto;
import br.ufrn.imd.markethub.service.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@AllArgsConstructor

public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance/{userId}")
    public ResponseEntity<WalletBalanceDto> getBalance(@PathVariable UUID userId) {
        WalletBalanceDto balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

//    @GetMapping("/history/{userId}")
//    public ResponseEntity<List<WalletHistoryDto>> getHistory(@PathVariable UUID userId) {
//        List<WalletHistoryDto> history = walletService.getHistory(userId);
//
////        List<WalletHistoryResponse> history = new ArrayList<>();
////        history.add(new WalletHistoryResponse("2025-01-01T10:00:00", 500));
////        history.add(new WalletHistoryResponse("2025-01-01T15:30:00", 200));
//
//        return ResponseEntity.ok(history);
//    }
}
