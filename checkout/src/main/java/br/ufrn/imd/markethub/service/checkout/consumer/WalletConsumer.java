package br.ufrn.imd.markethub.service.checkout.consumer;

import br.ufrn.imd.markethub.service.checkout.service.CheckoutService;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawDoneDto;
import br.ufrn.imd.markethub.service.checkout.thirdparty.wallet.WithdrawFailedDto;
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
public class WalletConsumer {

    private static final Logger logger = LoggerFactory.getLogger(WalletConsumer.class);

    private ObjectMapper objectMapper;
    private CheckoutService checkoutService;

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "withdraw_done", durable = "true"),
            exchange = @Exchange(value = "wallet", type = ExchangeTypes.TOPIC)
    ))
    public void handleWalletWithdraw(WithdrawDoneDto withdrawDoneDto) {
        logger.info("Received wallet withdraw message: {}", log(withdrawDoneDto));
        checkoutService.done(withdrawDoneDto.getCheckoutId());
    }

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "withdraw_failed", durable = "true"),
            exchange = @Exchange(value = "wallet", type = ExchangeTypes.TOPIC)
    ))
    public void handleWalletFailed(WithdrawFailedDto withdrawFailedDto) {
        logger.info("Received wallet failed message: {}", log(withdrawFailedDto));
        checkoutService.fail(withdrawFailedDto.getCheckoutId(), withdrawFailedDto.getReason());
    }

    private String log(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return "" + object;
        }
    }

}
