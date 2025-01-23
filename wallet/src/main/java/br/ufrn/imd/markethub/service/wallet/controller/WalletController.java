package br.ufrn.imd.markethub.service.wallet.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@AllArgsConstructor

public class WalletController {

    @Data
    @AllArgsConstructor
    public static class WalletBalanceResponse{
        private String userId;
        private int balance;
    }

    @Data
    @AllArgsConstructor
    public static class WalletHistoryResponse{
        private String timestamp;
        private int change;
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable String userId) {
        WalletBalanceResponse response = new WalletBalanceResponse(userId, 1000);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WalletHistoryResponse>> getHistory(@PathVariable String userId) {
        List<WalletHistoryResponse> history = new ArrayList<>();
        history.add(new WalletHistoryResponse("2025-01-01T10:00:00", 500));
        history.add(new WalletHistoryResponse("2025-01-01T15:30:00", 200));

        return ResponseEntity.ok(history);
    }
//    @GetMapping("/history")
//    public ResponseEntity<Integer> testBalance(@PathVariable String userId) {
//        //WalletBalanceResponse response = new WalletBalanceResponse(userId, 1000);
//        return ResponseEntity.ok(1);
//    }
}
