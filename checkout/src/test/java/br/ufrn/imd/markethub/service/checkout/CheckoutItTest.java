package br.ufrn.imd.markethub.service.checkout;

import br.ufrn.imd.markethub.service.checkout.base.TestBase;
import br.ufrn.imd.markethub.service.checkout.domain.CheckoutStatus;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.product.ProductDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawDoneDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawFailedDto;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CheckoutItTest extends TestBase {

    private final UUID userId = UUID.fromString("ec408cc0-e981-4673-bd1b-905aaf96342f");
    private final ProductDto product = ProductDto.builder()
            .id(UUID.fromString("740c6e0e-2653-41f2-b5e4-b669a037c2f5"))
            .name("Test Product")
            .price(200L)
            .quantity(10L)
            .build();
    final CheckoutRequestDto dto = CheckoutRequestDto.builder()
            .userId(userId)
            .productIds(List.of(product.getId()))
            .build();

    @BeforeEach
    void clearWiremock() {
        wireMock.resetMappings();
    }

    @Test
    @SneakyThrows
    void testCheckoutSuccessFlow() {
        wireMock.register(WireMock.get(WireMock.urlEqualTo("/products/" + product.getId()))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(product))));

        // First we submit a checkout
        final CheckoutDto created = submitCheckout(dto);
        final UUID checkoutId = created.getId();
        assertThat(checkoutId).isNotNull();
        assertThat(created.getUserId()).isEqualTo(userId);
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getProductIds()).isNotEmpty();
        assertThat(created.getTotal()).isPositive();
        assertThat(created.getStatus()).isEqualTo(CheckoutStatus.SUBMITTED);

        // We are able to retrieve it
        final CheckoutDto found = retrieveCheckout(checkoutId);
        assertThat(found.getId()).isEqualTo(checkoutId);
        assertThat(found.getUserId()).isEqualTo(created.getUserId());
        assertThat(found.getStatus()).isEqualTo(created.getStatus());
        assertThat(found.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(created.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));

        // The wallet service listens for it and publishes a withdraw done
        rabbitTemplate.convertAndSend("wallet", "withdraw_done", WithdrawDoneDto.builder()
                .userId(userId)
                .amount(100)
                .checkoutId(checkoutId)
                .build());

        await()
                .atMost(Duration.ofSeconds(1))
                .pollInterval(Duration.ofMillis(250))
                .untilAsserted(() -> assertThat(retrieveCheckout(checkoutId).getStatus()).isEqualTo(CheckoutStatus.DONE));
    }

    @Test
    void testCheckoutFailedFlow() throws Exception {
        wireMock.register(WireMock.get(WireMock.urlEqualTo("/products/" + product.getId()))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(product))));

        // First we submit a checkout
        final CheckoutDto created = submitCheckout(dto);
        final UUID checkoutId = created.getId();
        assertThat(checkoutId).isNotNull();
        assertThat(created.getUserId()).isEqualTo(userId);
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getStatus()).isEqualTo(CheckoutStatus.SUBMITTED);

        // We are able to retrieve it
        final CheckoutDto found = retrieveCheckout(checkoutId);
        assertThat(found.getId()).isEqualTo(checkoutId);
        assertThat(found.getUserId()).isEqualTo(created.getUserId());
        assertThat(found.getStatus()).isEqualTo(created.getStatus());
        assertThat(found.getCreatedAt().truncatedTo(ChronoUnit.SECONDS)).isEqualTo(created.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));

        // The wallet service listens for it and publishes a withdraw done
        rabbitTemplate.convertAndSend("wallet", "withdraw_failed", WithdrawFailedDto.builder()
                .userId(userId)
                .amount(900)
                .checkoutId(checkoutId)
                .reason("Not enough funds")
                .build());

        await()
                .atMost(Duration.ofSeconds(1))
                .pollInterval(Duration.ofMillis(250))
                .untilAsserted(() -> assertThat(retrieveCheckout(checkoutId).getStatus()).isEqualTo(CheckoutStatus.FAILED));
    }

    private CheckoutDto retrieveCheckout(UUID checkoutId) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/checkout/" + checkoutId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CheckoutDto.class);
    }

    private CheckoutDto submitCheckout(CheckoutRequestDto dto) throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CheckoutDto.class);
    }

}
