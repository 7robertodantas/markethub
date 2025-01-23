package br.ufrn.imd.markethub.service.wallet.dto;

// Colocar o service do Wallet Status
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletBalanceDto {
    // user_id, amount
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("amount")
    private Integer amount;
}
