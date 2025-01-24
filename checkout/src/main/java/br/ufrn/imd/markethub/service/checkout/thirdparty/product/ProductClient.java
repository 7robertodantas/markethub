package br.ufrn.imd.markethub.service.checkout.thirdparty.product;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;
    private final Environment environment;

    public ProductDto getProduct(UUID productId) {
        return restTemplate.getForObject(environment.getProperty("third-party.product-service.url") + "/products/" + productId, ProductDto.class);
    }

}
