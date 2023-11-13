package org.wex.infrastructure.rest.treasury;

import java.util.List;

public class RateDataResponse {
    public List<RateExhange> data;

    public boolean isEmpty() {
        return data.isEmpty();
    }
}
