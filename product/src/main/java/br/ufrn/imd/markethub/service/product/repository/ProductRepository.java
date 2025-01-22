package br.ufrn.imd.markethub.service.product.repository;

import br.ufrn.imd.markethub.service.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByUserId(UUID userId, Pageable pageable);
}
