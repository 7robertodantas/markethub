package br.ufrn.imd.markethub.service.wallet.service;

import br.ufrn.imd.markethub.service.wallet.domain.Wallet;
import br.ufrn.imd.markethub.service.wallet.domain.WalletHistory;
import br.ufrn.imd.markethub.service.wallet.dto.WalletBalanceDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletHistoryDto;
import br.ufrn.imd.markethub.service.wallet.exception.ServerException;
import br.ufrn.imd.markethub.service.wallet.repository.WalletHistoryRepository;
import br.ufrn.imd.markethub.service.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    public WalletBalanceDto getBalance(UUID userId){
        final Wallet wallet = walletRepository.findByUserId(userId);
        if(wallet == null) {
            throw ServerException.notFound();
        }
        return toDto(wallet);
    }

    public Page<WalletHistoryDto> getHistory(UUID userId, Pageable pageable){
        return walletHistoryRepository.findByUserId(userId, pageable).map(this::toDto);
    }

    private WalletBalanceDto toDto(Wallet wallet){
        return WalletBalanceDto.builder()
                .userId(wallet.getUserId())
                .amount(wallet.getAmount())
                .build();
    }

    private WalletHistoryDto toDto(WalletHistory walletHistory){
        return WalletHistoryDto.builder()
                .amount(walletHistory.getAmount())
                .type(walletHistory.getType())
                .timestamp(walletHistory.getTimestamp())
                .build();
    }
}
