package org.wex.core.ports.outbound;

import jakarta.ws.rs.NotFoundException;
import org.wex.core.domain.Money;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TreasuryClientGateway {
    BigDecimal findRateExchangeByCurrency(String currency, LocalDate gte, LocalDate lt) throws NotFoundException;
}
