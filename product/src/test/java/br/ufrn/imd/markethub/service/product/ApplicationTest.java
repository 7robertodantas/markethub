package br.ufrn.imd.markethub.service.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
abstract class ApplicationTest {
	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine3.21");

	@Container
	@ServiceConnection
	static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3.13.7-management-alpine");

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected RabbitTemplate rabbitTemplate;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}


}
