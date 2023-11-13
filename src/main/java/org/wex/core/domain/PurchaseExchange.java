package org.wex.core.domain;


import java.math.BigDecimal;

public class PurchaseExchange {

    private PurchaseTransaction purchase;
    private BigDecimal currencyExchange;
    private BigDecimal convertedAmount;

    public PurchaseExchange(PurchaseTransaction purchase, BigDecimal currencyExchange) {
        this.purchase = purchase;
        this.currencyExchange = currencyExchange;
        this.convertedAmount = purchase.getAmount().exchange(currencyExchange).getValue();
    }

    public PurchaseTransaction getPurchase() {
        return purchase;
    }

    public BigDecimal getCurrencyExchange() {
        return currencyExchange;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }
}
