package org.wex.domain.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wex.core.domain.Money;
import org.wex.core.domain.Purchase;
import org.wex.core.domain.PurchaseExchange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class PurchaseExchangeTest {


    @Test
    void shouldConvertValuesSuccessfully() {
        Purchase purchase = new Purchase(
                UUID.randomUUID(),
                "test",
                Money.of(12.50),
                LocalDate.now(),
                ZonedDateTime.now()
        );

        var exchangeValue = BigDecimal.valueOf(2.50);

        PurchaseExchange purchaseExchange = new PurchaseExchange(purchase, exchangeValue);

        Assertions.assertNotNull(purchaseExchange.getPurchase());
        Assertions.assertNotNull(purchaseExchange.getConvertedAmount());
        Assertions.assertEquals(exchangeValue, purchaseExchange.getCurrencyExchange());
    }
}
