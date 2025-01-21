package br.ufrn.imd.markethub.service.checkout.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "checkout", schema = "checkout")
public class Checkout {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CheckoutStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
