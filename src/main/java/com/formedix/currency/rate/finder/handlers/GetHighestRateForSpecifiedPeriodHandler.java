package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.max;
import static java.util.Comparator.comparing;

public final class GetHighestRateForSpecifiedPeriodHandler extends RateHandler<BigDecimal>
{
    @Override
    protected BigDecimal getResult(final RequestData requestData)
    {
        if (datesSuppliedAreInDataRange(requestData)) {
            final LocalDate startDate = getLocalDateFromString.apply(requestData.getDateRange1());
            final LocalDate endDate = getLocalDateFromString.apply(requestData.getDateRange2());
            Map<LocalDate, List<CurrencyData>> dataInRange = getPeriodsInRange(requestData, startDate, endDate);
            if (dataInRange.size() > 0) {
                List<CurrencyData> rates = new ArrayList<>();
                dataInRange.forEach((key, value) -> addRateForSpecifiedCurrency(rates, dataInRange.get(key),
                        requestData));
                return extractMaxRate(rates);
            }
        }
        return ZERO;
    }

    private BigDecimal extractMaxRate(List<CurrencyData> rates)
    {
        return max(rates, comparing(cd -> cd.getRate()
                                            .get()))
                .getRate()
                .get();
    }
}
