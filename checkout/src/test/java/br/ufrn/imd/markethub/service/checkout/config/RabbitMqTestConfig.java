package br.ufrn.imd.markethub.service.checkout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqTestConfig {

    @Bean
    public Queue withdrawDone() {
        return new Queue("withdraw_done", true);
    }

    @Bean
    public Queue withdrawFailed() {
        return new Queue("withdraw_failed", true);
    }

    @Bean
    public TopicExchange walletExchange() {
        return new TopicExchange("wallet", true, false);
    }

    @Bean
    public Binding bindingWithdrawDone(Queue withdrawDone, TopicExchange walletExchange) {
        return BindingBuilder.bind(withdrawDone)
                .to(walletExchange)
                .with("withdraw_done");
    }

    @Bean
    public Binding bindingWithdrawFailed(Queue withdrawFailed, TopicExchange walletExchange) {
        return BindingBuilder.bind(withdrawFailed)
                .to(walletExchange)
                .with("withdraw_failed");
    }

}
