package br.ufrn.imd.markethub.service.wallet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "wallet_balance")
public class WalletBalance {
    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "amount")
    private Long amount;
}
