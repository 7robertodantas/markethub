package br.ufrn.imd.markethub.service.wallet.repository;

import br.ufrn.imd.markethub.service.wallet.domain.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Wallet findByUserId(UUID userId);
}
