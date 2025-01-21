package br.ufrn.imd.markethub.service.checkout.dto;

import br.ufrn.imd.markethub.service.checkout.domain.CheckoutStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @JsonProperty("created_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, timezone = "UTC")
    private LocalDateTime createdAt = LocalDateTime.now();
}
