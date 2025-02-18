package br.ufrn.imd.markethub.service.checkout.base;

import br.ufrn.imd.markethub.service.checkout.CheckoutApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(
        classes = { CheckoutApplication.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
        classes = {
                TestConfig.class
        },
        initializers = {
                TestInitializer.class
        }
)
@Testcontainers
public abstract class TestBase {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected RabbitTemplate rabbitTemplate;

    @Autowired
    protected Environment environment;

    protected WireMock wireMock;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        wireMock = WireMock.create()
                .host(environment.getProperty("wiremock.host"))
                .port(environment.getProperty("wiremock.port", Integer.class))
                .build();
    }

}
