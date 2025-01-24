package br.ufrn.imd.markethub.service.wallet;

import br.ufrn.imd.markethub.service.wallet.base.TestBase;
import br.ufrn.imd.markethub.service.wallet.domain.WalletBalance;
import br.ufrn.imd.markethub.service.wallet.thirdparty.checkout.CheckoutDto;
import br.ufrn.imd.markethub.service.wallet.thirdparty.checkout.CheckoutStatus;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class CheckoutWithdrawTest extends TestBase {

    private static final Logger log = Logger.getLogger(CheckoutWithdrawTest.class.getName());
    private static final UUID USER_ID = UUID.fromString("1657d1f4-905a-4174-b7bf-c34a769df709");

    @Test
    void testHandleCheckoutSubmittedWithdraw() {
        createWallet();

        final CheckoutDto dto = CheckoutDto.builder()
                .id(UUID.fromString("ee53e637-bef5-47ec-afa1-ac1696e8fdd0"))
                .userId(USER_ID)
                .total(800L)
                .status(CheckoutStatus.SUBMITTED)
                .productIds(List.of(UUID.fromString("ee9f7f4a-93b1-487c-8eca-eec0a4ca1f41")))
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Sending message to checkout_submitted...");
        rabbitTemplate.convertAndSend("checkout", "checkout_submitted", dto);

        await()
                .atMost(Duration.ofSeconds(3))
                .pollInterval(Duration.ofMillis(250))
                .untilAsserted(() -> {
                    final WalletBalance updated = walletRepository.findByUserId(USER_ID);
                    assertThat(updated.getAmount()).isEqualTo(200);
                });
    }

    private WalletBalance createWallet() {
        final WalletBalance wallet = new WalletBalance();
        wallet.setUserId(USER_ID);
        wallet.setAmount(1000L);
        return walletRepository.save(wallet);
    }
}