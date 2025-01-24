package br.ufrn.imd.markethub.service.checkout.thirdparty.product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;
    private final @Value("${third-party.product-service.url}") String baseUrl;

    public ProductDto getProduct(UUID productId) {
        return restTemplate.getForObject(baseUrl + "/products/" + productId, ProductDto.class);
    }

}
