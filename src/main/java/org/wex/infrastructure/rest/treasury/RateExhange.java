package org.wex.infrastructure.rest.treasury;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RateExhange {
    @JsonAlias("country_currency_desc")
    public String countryCurrencyDesc;

    @JsonAlias("exchange_rate")
    public String exchangeRate;

    @JsonAlias("record_date")
    public LocalDate recordDate;

    public BigDecimal getExchangeRate() {
        return new BigDecimal(this.exchangeRate);
    }
}
