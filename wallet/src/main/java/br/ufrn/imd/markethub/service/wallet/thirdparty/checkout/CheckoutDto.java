package br.ufrn.imd.markethub.service.wallet.thirdparty.checkout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("status")
    private CheckoutStatus status;

    @JsonProperty("product_ids")
    private List<UUID> productIds;

    @JsonProperty("total")
    private Long total;

    @JsonProperty("created_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, timezone = "UTC")
    private LocalDateTime createdAt = LocalDateTime.now();
}
