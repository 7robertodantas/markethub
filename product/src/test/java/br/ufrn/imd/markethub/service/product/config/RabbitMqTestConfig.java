package br.ufrn.imd.markethub.service.product.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTestConfig {

    @Bean
    public Queue checkoutDone() {
        return new Queue("checkout_done", true);
    }

    @Bean
    public TopicExchange checkoutExchange() {
        return new TopicExchange("checkout", true, false);
    }

    @Bean
    public Binding bindingCheckoutDone(Queue checkoutDone, TopicExchange checkoutExchange) {
        return BindingBuilder.bind(checkoutDone)
                .to(checkoutExchange)
                .with("checkout_done");
    }
}