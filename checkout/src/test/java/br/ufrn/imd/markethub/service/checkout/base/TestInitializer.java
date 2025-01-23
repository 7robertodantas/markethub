package br.ufrn.imd.markethub.service.checkout.base;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;

import java.util.Map;
import java.util.logging.Logger;

/**
 * This initializer runs all dependencies required by this service.
 * It will automatically run a mysql, redis, mockserver, and replace their respective environment variables from the properties.
 * This is also used when running integration tests.
 */
public class TestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger log = Logger.getLogger(TestInitializer.class.getName());

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine3.21");
    private static final RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3.13.7-management-alpine");

    @SneakyThrows
    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        postgres.start();
        rabbitmq.start();

        apply(applicationContext, Map.of(
            "spring.datasource.url", postgres.getJdbcUrl(),
            "spring.datasource.username", postgres.getUsername(),
            "spring.datasource.password", postgres.getPassword(),
            "spring.rabbitmq.host", rabbitmq.getHost(),
            "spring.rabbitmq.port", rabbitmq.getAmqpPort().toString(),
            "spring.rabbitmq.username", rabbitmq.getAdminUsername(),
            "spring.rabbitmq.password", rabbitmq.getAdminPassword()
        ));
    }

    private void apply(ConfigurableApplicationContext applicationContext, Map<String, String> override) {
        override.forEach((key, value) -> {
            log.info(String.format("Overriding `%s` property to `%s`", key, value));
        });

        TestPropertyValues.of(override.entrySet()
                .stream()
                .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue())))
                .applyTo(applicationContext);
    }

}
