package br.ufrn.imd.markethub.service.product.dto;


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
public class CheckoutDoneDto {
    @JsonProperty("product_id")
    private UUID productId;
    @JsonProperty("quantity")
    private int quantity;
}