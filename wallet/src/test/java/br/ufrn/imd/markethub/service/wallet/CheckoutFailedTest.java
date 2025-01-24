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

class CheckoutFailedTest extends TestBase {

    private static final Logger log = Logger.getLogger(CheckoutFailedTest.class.getName());
    private static final UUID USER_ID = UUID.fromString("e86fb64c-3b15-4386-b6ff-4dd2635a41a8");
    private static final Long USER_BALANCE = 1000L;
    private static final Long CHECKOUT_TOTAL = 1200L;

    @Test
    void testHandleCheckoutSubmittedWithdraw() {
        createWallet();

        final CheckoutDto dto = CheckoutDto.builder()
                .id(UUID.fromString("88beab88-84f5-45b7-ba16-2ec2c15c6215"))
                .userId(USER_ID)
                .total(CHECKOUT_TOTAL)
                .status(CheckoutStatus.SUBMITTED)
                .productIds(List.of(UUID.fromString("cd812e48-6306-4a4c-9721-79e01c854569")))
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Sending message to checkout_submitted...");
        rabbitTemplate.convertAndSend("checkout", "checkout_submitted", dto);

        await()
                .atMost(Duration.ofSeconds(3))
                .pollInterval(Duration.ofMillis(250))
                .untilAsserted(() -> {
                    final WalletBalance updated = walletRepository.findByUserId(USER_ID);
                    assertThat(updated.getAmount()).isEqualTo(USER_BALANCE);
                });
    }

    private WalletBalance createWallet() {
        final WalletBalance wallet = new WalletBalance();
        wallet.setUserId(USER_ID);
        wallet.setAmount(USER_BALANCE);
        return walletRepository.save(wallet);
    }
}