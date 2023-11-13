package org.wex.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.wex.core.domain.Money;
import org.wex.core.domain.Purchase;
import org.wex.infrastructure.web.validator.custom.DateISO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreatePurchaseDTO {

    @Length(max = 50)
    @NotBlank
    public String description;

    @NotNull
    @Positive
    public BigDecimal amount;

    @DateISO
    @NotNull
    public String transactionDate;

    public Purchase toDomain() {
        return new Purchase(
                description,
                Money.of(amount),
                LocalDate.parse(this.transactionDate)
        );
    }
}
