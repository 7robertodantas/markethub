package br.ufrn.imd.markethub.service.checkout.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Configuration
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.additionalInterceptors(new RestTemplateLoggingInterceptor())
                .build();
    }

    private static class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            final Instant startTime = Instant.now();
            try {
                final ClientHttpResponse response = execution.execute(request, body);
                final long duration = Duration.between(startTime, Instant.now()).toMillis();
                logger.info("Outgoing request method={}, url={}, status-code={}, ms={}",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusCode().value(),
                        duration);
                return response;
            } catch (Exception e) {
                final long duration = Duration.between(startTime, Instant.now()).toMillis();
                logger.info("Outgoing request method={}, url={}, exception={}, cause={}, ms={}",
                        request.getMethod(),
                        request.getURI(),
                        e.getClass().getSimpleName(),
                        Optional.ofNullable(e.getCause())
                                .map(Throwable::getClass)
                                .map(Class::getSimpleName)
                                .orElse(""),
                        duration);
                throw e;
            }
        }
    }

}
