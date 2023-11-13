package org.wex.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.wex.core.domain.Money;
import org.wex.core.domain.Purchase;
import org.wex.core.domain.PurchaseExchange;
import org.wex.core.ports.outbound.TreasuryClientGateway;
import org.wex.infrastructure.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class PurchaseConverterService {

    private final TreasuryClientGateway treasuryClientGateway;

    public PurchaseConverterService(TreasuryClientGateway treasuryClientGateway) {
        this.treasuryClientGateway = treasuryClientGateway;
    }

    public PurchaseExchange convertToCurrency(UUID id, String currency) {
        PurchaseEntity purchaseEntity = (PurchaseEntity) PurchaseEntity.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        LocalDate initialDate = purchaseEntity.transactionDate.minusMonths(6);

        BigDecimal exchangeValue = this.treasuryClientGateway.findRateExchangeByCurrency(
                currency, initialDate, purchaseEntity.transactionDate);

        Purchase purchase = new Purchase(
                purchaseEntity.id,
                purchaseEntity.description,
                Money.of(purchaseEntity.amount),
                purchaseEntity.transactionDate,
                purchaseEntity.createdAt
        );

        return purchase.convertToExchangeCurrency(exchangeValue);
    }
}
