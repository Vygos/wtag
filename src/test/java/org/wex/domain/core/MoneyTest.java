package org.wex.domain.core;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.wex.core.domain.Money;

import java.math.BigDecimal;

public class MoneyTest {

    @Test
    void shouldRoundMoneyHalfUpSuccessfully() {
        Money money = Money.of(12.456);

        var expectedValue = BigDecimal.valueOf(12.46);

        Assertions.assertEquals(expectedValue, money.getValue());
    }

    @Test
    void shouldCalculateExchangeCurrencySuccessfully() {
        Money money = Money.of(12.86);

        Money exchange = money.exchange(BigDecimal.valueOf(5.286));

        var expectedValue = BigDecimal.valueOf(67.98);

        Assertions.assertEquals(expectedValue, exchange.getValue());
    }
}
