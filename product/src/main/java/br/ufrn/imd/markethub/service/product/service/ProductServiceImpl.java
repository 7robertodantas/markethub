package br.ufrn.imd.markethub.service.product.service;

import br.ufrn.imd.markethub.service.product.domain.Product;
import br.ufrn.imd.markethub.service.product.dto.CheckoutDoneDto;
import br.ufrn.imd.markethub.service.product.dto.ErrorDto;
import br.ufrn.imd.markethub.service.product.dto.ProductDto;
import br.ufrn.imd.markethub.service.product.exception.ServerException;
import br.ufrn.imd.markethub.service.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public List<ProductDto> findAllAvailableProducts() {
        final List<Product> products = repository.findAll();
        return products.stream()
                .map(ProductServiceImpl::toDto)
                .toList();
    }

    public ProductDto findProductById(UUID id) {
        final Product product = repository.findById(id)
                .orElseThrow(ServerException::notFound);
        return toDto(product);
    }

    private static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    @Override
    public void processCheckoutDone(CheckoutDoneDto checkoutDoneDto) {
        final Product product = repository.findById(checkoutDoneDto.getProductId())
                .orElseThrow(() -> new ServerException(new ErrorDto(404, "Product not found")));

        if (product.getQuantity() < checkoutDoneDto.getQuantity()) {
            throw new ServerException(new ErrorDto(409, "Insufficient stock for product: " + product.getName()));
        }

        product.setQuantity(product.getQuantity() - checkoutDoneDto.getQuantity());
        repository.save(product);
    }
}
