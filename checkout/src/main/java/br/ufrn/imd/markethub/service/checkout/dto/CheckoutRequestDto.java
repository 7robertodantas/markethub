package br.ufrn.imd.markethub.service.checkout.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequestDto {
    @JsonProperty("user_id")
    private UUID userId;

    @JsonProperty("product_ids")
    private List<UUID> productIds;
}
