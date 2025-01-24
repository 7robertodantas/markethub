package br.ufrn.imd.markethub.service.product.base;

import br.ufrn.imd.markethub.service.product.ProductApplication;
import br.ufrn.imd.markethub.service.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest(
        classes = { ProductApplication.class },
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
    protected ProductRepository productRepository;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

}
