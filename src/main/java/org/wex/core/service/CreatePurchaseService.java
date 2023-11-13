package org.wex.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.wex.core.domain.Money;
import org.wex.core.domain.Purchase;
import org.wex.infrastructure.entity.PurchaseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class CreatePurchaseService {

    @Transactional
    public Purchase create(Purchase purchase) {
        PurchaseEntity entity = purchase.toEntity();
        entity.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
        entity.persist();

        return new Purchase(
                entity.id,
                entity.description,
                Money.of(entity.amount),
                entity.transactionDate,
                entity.createdAt
        );
    }
}
