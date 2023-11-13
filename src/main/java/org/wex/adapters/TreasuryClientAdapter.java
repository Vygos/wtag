package org.wex.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.wex.core.ports.outbound.TreasuryClientGateway;
import org.wex.infrastructure.rest.treasury.RateDataResponse;
import org.wex.infrastructure.rest.treasury.RateOfExchange;

import java.math.BigDecimal;
import java.time.LocalDate;

@ApplicationScoped
public class TreasuryClientAdapter implements TreasuryClientGateway {

    private final RateOfExchange rateOfExchange;

    public TreasuryClientAdapter(@RestClient RateOfExchange rateOfExchange) {
        this.rateOfExchange = rateOfExchange;
    }

    @Override
    public BigDecimal findRateExchangeByCurrency(String currency, LocalDate gte, LocalDate lt) throws NotFoundException {
        RateDataResponse exchangeRateResp = this.rateOfExchange.findExchangeRate(currency, gte, lt);

        if (exchangeRateResp.isEmpty()) {
            var response = Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(String.format("Exchange rate wasn't found for the date %s", gte)))
                    .build();

            throw new NotFoundException(response);
        }

        return exchangeRateResp.data.get(0).getExchangeRate();
    }

    public record ErrorResponse(String error) {
    }
}
