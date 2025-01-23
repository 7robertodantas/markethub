package br.ufrn.imd.markethub.service.wallet.service;

import br.ufrn.imd.markethub.service.wallet.domain.Wallet;
import br.ufrn.imd.markethub.service.wallet.dto.ErrorDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletBalanceDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletHistoryDto;
import br.ufrn.imd.markethub.service.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletBalanceDto getBalance(UUID userId){
        Wallet wallet = walletRepository.findByUserId(userId);
        if(wallet == null) {
            // Deu erro tentando usar ErrorDto
        }
        return new WalletBalanceDto(wallet.getUserId(), wallet.getAmount());
    }

    public WalletHistoryDto getHistory(UUID userId){
        Wallet wallet = walletRepository.findByUserId(userId);
        List<WalletHistoryDto> historyList;
        if(wallet == null){
            // Throw new exception
        }

        // De que forma posso pegar essa lista do histórico?
        //return new WalletHistoryDto(historyList);
        return new WalletHistoryDto("2025-01-01T10:00:00", 500);
    }
}
