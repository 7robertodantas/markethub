package br.ufrn.imd.markethub.service.checkout.domain;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "checkout")
public class Checkout {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    @Type(JsonType.class)
    @Column(name = "product_ids", columnDefinition = "jsonb")
    private List<UUID> productIds;

    @Column(name = "total")
    private Long total;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
