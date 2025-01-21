package br.ufrn.imd.markethub.service.checkout.thirdparty.wallet;

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
public class WithdrawFailedDto {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("checkout_id")
    private UUID checkoutId;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("reason")
    private String reason;
}
