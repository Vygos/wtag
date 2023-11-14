package org.wex.infrastructure.web.controller;

import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wex.core.ports.outbound.TreasuryClientGateway;
import org.wex.infrastructure.entity.PurchaseEntity;
import org.wex.infrastructure.web.dto.CreatePurchaseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PurchaseControllerTest {

    @InjectMock
    TreasuryClientGateway treasuryClientGateway;

    @Test
    void shouldCreateAPurchaseSuccessfully() {
        var date = LocalDate.now().toString();
        var createPurchase = new CreatePurchaseDTO();
        createPurchase.description = "RTX Nvidia 4080";
        createPurchase.amount = BigDecimal.valueOf(980.50);
        createPurchase.transactionDate = date;


        given().contentType(ContentType.JSON)
                .body(createPurchase)
                .basePath("/purchase")
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", Matchers.notNullValue())
                .body("description", Matchers.equalTo(createPurchase.description))
                .body("amount.value", Matchers.equalTo(980.5f))
                .body("transactionDate", Matchers.equalTo(date));
    }

    @Test
    void shouldCreateAPurchaseAndRoundToNearestCent() {
        var date = LocalDate.now().toString();
        var createPurchase = new CreatePurchaseDTO();
        createPurchase.description = "RTX Nvidia 4080";
        createPurchase.amount = BigDecimal.valueOf(980.687);
        createPurchase.transactionDate = date;


        given().contentType(ContentType.JSON)
                .body(createPurchase)
                .basePath("/purchase")
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", Matchers.notNullValue())
                .body("description", Matchers.equalTo(createPurchase.description))
                .body("amount.value", Matchers.equalTo(980.69f))
                .body("transactionDate", Matchers.equalTo(date));
    }

    @Test
    void shouldReturn404NotFoundWhenDoesNotFindAPurchase() {
        var date = LocalDate.now().toString();
        var createPurchase = new CreatePurchaseDTO();
        createPurchase.description = "RTX Nvidia 4080";
        createPurchase.amount = BigDecimal.valueOf(980.687);
        createPurchase.transactionDate = date;

        given()
                .get("/purchase" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test
    void shouldReturn200OKWhenHaveExchangeRateValueAvailable() {
        UUID[] purchaseId = new UUID[1];
        QuarkusTransaction.requiringNew().run(() -> {
            PurchaseEntity purchase = new PurchaseEntity();
            purchase.amount = BigDecimal.valueOf(250.50);
            purchase.description = "test";
            purchase.transactionDate = LocalDate.now();
            purchase.createdAt = ZonedDateTime.now();
            purchase.persist();
            purchaseId[0] = purchase.id;
        });

        Mockito.when(treasuryClientGateway.findRateExchangeByCurrency(Mockito.eq("Brazil-Real"), Mockito.any(), Mockito.any()))
                .thenReturn(BigDecimal.valueOf(5.50));

        given()
                .get("/purchase/{id}/currency/{currency}/convert", purchaseId[0].toString(), "Brazil-Real")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("currencyExchange", Matchers.equalTo(5.50f))
                .body("convertedAmount", Matchers.equalTo(1377.75f));

    }
}
