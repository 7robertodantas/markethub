package br.ufrn.imd.markethub.service.wallet.service;

import br.ufrn.imd.markethub.service.wallet.consumer.CheckoutSubmittedConsumer;
import br.ufrn.imd.markethub.service.wallet.domain.TransactionType;
import br.ufrn.imd.markethub.service.wallet.domain.WalletBalance;
import br.ufrn.imd.markethub.service.wallet.domain.WalletHistory;
import br.ufrn.imd.markethub.service.wallet.dto.WalletBalanceDto;
import br.ufrn.imd.markethub.service.wallet.dto.WalletHistoryDto;
import br.ufrn.imd.markethub.service.wallet.exception.ServerException;
import br.ufrn.imd.markethub.service.wallet.repository.WalletHistoryRepository;
import br.ufrn.imd.markethub.service.wallet.repository.WalletRepository;
import br.ufrn.imd.markethub.service.wallet.thirdparty.checkout.CheckoutDto;
import br.ufrn.imd.markethub.service.wallet.thirdparty.checkout.CheckoutStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    public void applyCheckout(CheckoutDto checkout) {
        if (!CheckoutStatus.SUBMITTED.equals(checkout.getStatus())) {
            throw ServerException.conflict("Only submitted checkouts can be applied");
        }

        final WalletBalance wallet = walletRepository.findByUserId(checkout.getUserId());
        if(wallet == null) {
            throw ServerException.notFound();
        }

        if (wallet.getAmount() < checkout.getTotal()) {
            throw ServerException.conflict("Insufficient funds");
        }

        final long newAmount = wallet.getAmount() - checkout.getTotal();
        wallet.setAmount(newAmount);
        logger.debug("Updating wallet amount to {}", newAmount);
        walletRepository.saveAndFlush(wallet);

        final WalletHistory history = new WalletHistory();
        history.setUserId(checkout.getUserId());
        history.setAmount(checkout.getTotal());
        history.setTimestamp(Instant.now());
        history.setType(TransactionType.WITHDRAWAL);
        walletHistoryRepository.save(history);
    }

    public WalletBalanceDto getBalance(UUID userId){
        final WalletBalance wallet = walletRepository.findByUserId(userId);
        if(wallet == null) {
            throw ServerException.notFound();
        }
        return toDto(wallet);
    }

    public Page<WalletHistoryDto> getHistory(UUID userId, Pageable pageable){
        return walletHistoryRepository.findByUserId(userId, pageable).map(this::toDto);
    }

    private WalletBalanceDto toDto(WalletBalance wallet){
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
