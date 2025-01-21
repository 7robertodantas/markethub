package br.ufrn.imd.markethub.service.checkout.producer;

import br.ufrn.imd.markethub.service.checkout.constants.RabbitMqConstants;
import br.ufrn.imd.markethub.service.checkout.dto.CheckoutDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckoutPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendCheckoutSubmitted(CheckoutDto checkout) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_NAME, RabbitMqConstants.CHECKOUT_SUBMITTED, checkout);
    }

    public void sendCheckoutDone(CheckoutDto checkout) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_NAME, RabbitMqConstants.CHECKOUT_DONE, checkout);
    }

    public void sendCheckoutFailed(CheckoutDto checkout) {
        rabbitTemplate.convertAndSend(RabbitMqConstants.EXCHANGE_NAME, RabbitMqConstants.CHECKOUT_FAILED, checkout);
    }

}
