package br.ufrn.imd.markethub.service.wallet.repository;

import br.ufrn.imd.markethub.service.wallet.domain.WalletBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletBalance, UUID> {
    WalletBalance findByUserId(UUID userId);
}
