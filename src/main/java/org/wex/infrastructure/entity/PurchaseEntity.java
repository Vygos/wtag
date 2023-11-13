package org.wex.infrastructure.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchase")
public class PurchaseEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String description;

    public BigDecimal amount;

    @Column(name = "transaction_date")
    public LocalDate transactionDate;

    @Column(name = "created_at")
    public ZonedDateTime createdAt;

}
