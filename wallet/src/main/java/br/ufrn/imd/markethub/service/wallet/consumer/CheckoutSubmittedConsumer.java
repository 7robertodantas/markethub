package br.ufrn.imd.markethub.service.wallet.consumer;

import br.ufrn.imd.markethub.service.wallet.service.WalletService;
import br.ufrn.imd.markethub.service.wallet.thirdparty.checkout.CheckoutDto;
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
public class CheckoutSubmittedConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutSubmittedConsumer.class);

    private final WalletService walletService;

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "checkout_submitted", durable = "true"),
            exchange = @Exchange(value = "checkout", type = ExchangeTypes.TOPIC)
    ))
    public void handleCheckout(CheckoutDto dto) {
        logger.info("Received checkout_done message: {}", dto);
        try {
            walletService.applyCheckout(dto);
            logger.info("Successfully processed checkout_submitted");
        } catch (Exception e) {
            logger.error("Failed to process checkout_submitted", e);
        }
    }
}
