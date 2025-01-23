package br.ufrn.imd.markethub.service.product;

import br.ufrn.imd.markethub.service.product.domain.Product;
import br.ufrn.imd.markethub.service.product.dto.CheckoutDoneDto;
import br.ufrn.imd.markethub.service.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutDoneConsumerTest extends ApplicationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();

        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setQuantity(10);
        product.setValue(BigDecimal.valueOf(100));

        productRepository.saveAndFlush(product);
    }

    @Test
    void testHandleCheckoutDone_Success() throws Exception {
        CheckoutDoneDto dto = new CheckoutDoneDto();
        dto.setProductId(productId);
        dto.setQuantity(2);

        String message = objectMapper.writeValueAsString(dto);
        System.out.println("Enviando mensagem para a fila checkout_done...");
        rabbitTemplate.convertAndSend("checkout", "checkout_done", message);

        Thread.sleep(3000);

        Product updated = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Produto não encontrado após checkout_done!"));

        assertEquals(8, updated.getQuantity(),
                "A quantidade do produto não foi decrementada corretamente!");
    }
}