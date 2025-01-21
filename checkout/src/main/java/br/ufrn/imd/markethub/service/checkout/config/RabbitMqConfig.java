package br.ufrn.imd.markethub.service.checkout.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue checkoutDone() {
        return new Queue("checkout_done", true);
    }

    @Bean
    public Queue checkoutFailed() {
        return new Queue("checkout_failed", true);
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

    @Bean
    public Binding bindingCheckoutFailed(Queue checkoutFailed, TopicExchange checkoutExchange) {
        return BindingBuilder.bind(checkoutFailed)
                .to(checkoutExchange)
                .with("checkout_failed");
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter,
                                         ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

}
