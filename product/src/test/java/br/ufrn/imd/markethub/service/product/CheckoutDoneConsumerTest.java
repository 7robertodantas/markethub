package br.ufrn.imd.markethub.service.product;

import br.ufrn.imd.markethub.service.product.base.TestBase;
import br.ufrn.imd.markethub.service.product.domain.Product;
import br.ufrn.imd.markethub.service.product.dto.CheckoutDoneDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class CheckoutDoneConsumerTest extends TestBase {

    private static final Logger log = Logger.getLogger(CheckoutDoneConsumerTest.class.getName());

    @Test
    void testHandleCheckoutDone_Success() {
        final Product product = createProduct();

        final CheckoutDoneDto dto = CheckoutDoneDto.builder()
                .productId(product.getId())
                .quantity(2)
                .build();

        log.info("Sending message to checkout_done...");
        rabbitTemplate.convertAndSend("checkout", "checkout_done", dto);

        await()
                .atMost(Duration.ofSeconds(3))
                .pollInterval(Duration.ofMillis(100))
                .untilAsserted(() -> {
                    final Product updated = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalStateException("Produto não encontrado após checkout_done!"));
                    assertThat(updated.getQuantity()).isEqualTo(8);
                });
    }

    private Product createProduct() {
        final Product product = new Product();
        product.setName("Test Product");
        product.setQuantity(10L);
        product.setPrice(100L);
        return productRepository.save(product);
    }
}