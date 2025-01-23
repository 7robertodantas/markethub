package br.ufrn.imd.markethub.service.checkout;

import br.ufrn.imd.markethub.service.checkout.base.TestBase;
import br.ufrn.imd.markethub.service.checkout.domain.CheckoutStatus;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutRequestDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawDoneDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawFailedDto;
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

    @Test
    void testCheckoutSuccessFlow() throws Exception {
        final UUID userId = UUID.fromString("ec408cc0-e981-4673-bd1b-905aaf96342f");
        final UUID product = UUID.fromString("baaf3a87-7cbd-433e-8e2d-e99a8649637a");
        final CheckoutRequestDto dto = CheckoutRequestDto.builder()
                .userId(userId)
                .productIds(List.of(product))
                .build();

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
        rabbitTemplate.convertAndSend("wallet", "withdraw_done", WithdrawDoneDto.builder()
                .userId(userId)
                .amount(100)
                .checkoutId(checkoutId).build());

        await()
                .atMost(Duration.ofSeconds(1))
                .pollInterval(Duration.ofMillis(250))
                .untilAsserted(() -> assertThat(retrieveCheckout(checkoutId).getStatus()).isEqualTo(CheckoutStatus.DONE));
    }

    @Test
    void testCheckoutFailedFlow() throws Exception {
        final UUID userId = UUID.fromString("729d74ff-6e4f-4690-b93d-a0f328892ddf");
        final UUID product = UUID.fromString("b2d969f5-f64b-4abc-808c-9f0a2ea25ea6");
        final CheckoutRequestDto dto = CheckoutRequestDto.builder()
                .userId(userId)
                .productIds(List.of(product))
                .build();

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
