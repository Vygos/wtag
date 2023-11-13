package org.wex.core.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private BigDecimal value;

    protected Money(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    protected Money(Double value) {
        this(new BigDecimal(value));
    }

    public static Money of(Double value) {
        return new Money(value);
    }

    public static Money of(BigDecimal value) {
        return new Money(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public Money exchange(BigDecimal exchangeValue) {
        return new Money(this.value.multiply(exchangeValue));
    }

}
