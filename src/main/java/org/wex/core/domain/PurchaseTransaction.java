package org.wex.core.domain;

import java.time.LocalDate;
import java.util.UUID;

public interface PurchaseTransaction {
    UUID getId();
    Money getAmount();
    String getDescription();
    LocalDate getTransactionDate();
}
