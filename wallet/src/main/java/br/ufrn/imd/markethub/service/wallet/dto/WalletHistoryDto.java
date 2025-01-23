package br.ufrn.imd.markethub.service.wallet.dto;

// Colocar o service do Wallet Status
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class WalletHistoryDto {
    // timestamp, change
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("change")
    private Integer change;
}
