package br.ufrn.imd.markethub.service.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
}