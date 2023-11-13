package org.wex.infrastructure.web.controller;

import jakarta.validation.Validator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.wex.core.domain.Purchase;
import org.wex.core.service.CreatePurchaseService;
import org.wex.core.service.PurchaseConverterService;
import org.wex.infrastructure.rest.treasury.RateDataResponse;
import org.wex.infrastructure.rest.treasury.RateOfExchange;
import org.wex.infrastructure.web.dto.CreatePurchaseDTO;
import org.wex.infrastructure.web.validator.ViolationsHandler;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@Path("purchase")
public class PurchaseController {

    private final Validator validator;
    private final CreatePurchaseService createPurchaseService;
    private final RateOfExchange rateOfExchange;
    private final PurchaseConverterService purchaseConverterService;

    public PurchaseController(
            Validator validator,
            CreatePurchaseService createPurchaseService,
            @RestClient RateOfExchange rateOfExchange,
            PurchaseConverterService purchaseConverterService
    ) {
        this.validator = validator;
        this.createPurchaseService = createPurchaseService;
        this.rateOfExchange = rateOfExchange;
        this.purchaseConverterService = purchaseConverterService;
    }

    @POST
    public Response create(CreatePurchaseDTO createPurchaseDTO) {
        ViolationsHandler.throwIfHasViolations(this.validator.validate(createPurchaseDTO));

        Purchase purchase = this.createPurchaseService.create(createPurchaseDTO.toDomain());
        return Response.created(URI.create("/purchase/" + purchase.getId())).entity(purchase).build();
    }

    @GET
    @Path("{id}/currency/{currency}/convert")
    public Response convertToCurrency(@RestPath UUID id, @RestPath String currency) {
        return Response.ok(this.purchaseConverterService.convertToCurrency(id, currency)).build();
    }
}
