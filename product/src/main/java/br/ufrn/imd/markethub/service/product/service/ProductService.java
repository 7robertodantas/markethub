package br.ufrn.imd.markethub.service.product.service;

import br.ufrn.imd.markethub.service.product.dto.CheckoutDoneDto;
import br.ufrn.imd.markethub.service.product.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> findAllAvailableProducts();

    ProductDto findProductById(UUID id);

    void processCheckoutDone(CheckoutDoneDto checkoutDoneDto);
}