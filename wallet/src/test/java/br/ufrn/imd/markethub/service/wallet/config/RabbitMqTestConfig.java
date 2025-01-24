package br.ufrn.imd.markethub.service.wallet.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTestConfig {

    @Bean
    public Queue checkoutSubmitted() {
        return new Queue("checkout_submitted", true);
    }

    @Bean
    public TopicExchange checkoutExchange() {
        return new TopicExchange("checkout", true, false);
    }

    @Bean
    public Binding bindingCheckoutSubmitted(Queue checkoutSubmitted, TopicExchange checkoutExchange) {
        return BindingBuilder.bind(checkoutSubmitted)
                .to(checkoutExchange)
                .with("checkout_submitted");
    }
}