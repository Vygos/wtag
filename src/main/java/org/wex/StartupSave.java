package org.wex;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.wex.infrastructure.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class StartupSave {

    @Transactional
    public void create(@Observes StartupEvent event) {
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.amount = BigDecimal.valueOf(25.50);
        purchase.description = "Purchase of a backpack";
        purchase.transactionDate = LocalDate.of(2023, 6, 2);
        purchase.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));

        purchase.persist();
    }
}
