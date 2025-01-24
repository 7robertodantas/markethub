package br.ufrn.imd.markethub.service.wallet.repository;

import br.ufrn.imd.markethub.service.wallet.domain.WalletHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletHistoryRepository extends JpaRepository<WalletHistory, UUID> {
    Page<WalletHistory> findByUserId(UUID userId, Pageable pageable);
}
