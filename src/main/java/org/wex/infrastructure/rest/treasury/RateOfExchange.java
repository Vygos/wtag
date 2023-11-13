package org.wex.infrastructure.rest.treasury;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import java.time.LocalDate;

@Path("/services/api/fiscal_service/v1/accounting/od/rates_of_exchange")
@RegisterRestClient(configKey = "treasury-api")
public interface RateOfExchange {

    @GET
    RateDataResponse findExchangeRate(@RestQuery String fields, @RestQuery String filter, @RestQuery String sort);

    default RateDataResponse findExchangeRate(String currencyName, LocalDate gte, LocalDate lte) {
        var filterBuilder = String.format("country_currency_desc:in:(%s),record_date:gte:%s,record_date:lt:%s",
                currencyName,
                gte.toString(),
                lte.toString()
        );

        var fieldsResults = "country_currency_desc,exchange_rate,record_date";
        var sort = "-record_date";

        return this.findExchangeRate(
                fieldsResults,
                filterBuilder,
                sort
        );
    }
}
