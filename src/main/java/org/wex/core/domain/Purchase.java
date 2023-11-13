package org.wex.core.domain;

import org.wex.infrastructure.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Purchase implements PurchaseTransaction {

    private UUID id;
    private String description;
    private Money amount;
    private LocalDate transactionDate;
    private ZonedDateTime createdAt;


    public Purchase(String description, Money amount, LocalDate transactionDate) {
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Purchase(UUID id, String description, Money amount, LocalDate transactionDate) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Purchase(UUID id, String description, Money amount, LocalDate transactionDate, ZonedDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }

    public PurchaseExchange convertToExchangeCurrency(BigDecimal currencyValue) {
        return new PurchaseExchange(this, currencyValue);
    }

    public PurchaseEntity toEntity() {
        PurchaseEntity entity = new PurchaseEntity();
        entity.id = id;
        entity.amount = amount.getValue();
        entity.description = description;
        entity.transactionDate = transactionDate;
        return entity;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

}
