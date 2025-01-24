package br.ufrn.imd.markethub.service.product.consumer;

import br.ufrn.imd.markethub.service.product.dto.CheckoutDoneDto;
import br.ufrn.imd.markethub.service.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckoutDoneConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutDoneConsumer.class);

    private final ObjectMapper objectMapper;
    private final ProductService productService;

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "checkout_done", durable = "true"),
            exchange = @Exchange(value = "checkout", type = ExchangeTypes.TOPIC)
    ))
    public void handleCheckoutDone(CheckoutDoneDto dto) {
        logger.info("Received checkout_done message: {}", dto);
        try {
            productService.processCheckoutDone(dto);
            logger.info("Successfully processed checkout_done for product ID: {}", dto.getProductId());
        } catch (Exception e) {
            logger.error("Failed to process checkout_done for product ID: {}. Reason: {}",
                    dto.getProductId(), e.getMessage(), e);
        }
    }
}
