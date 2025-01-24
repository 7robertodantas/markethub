package br.ufrn.imd.markethub.service.wallet.dto;

import br.ufrn.imd.markethub.service.wallet.domain.TransactionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletHistoryDto {
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("type")
    private TransactionType type;
    @JsonProperty("timestamp")
    private Instant timestamp;
}
