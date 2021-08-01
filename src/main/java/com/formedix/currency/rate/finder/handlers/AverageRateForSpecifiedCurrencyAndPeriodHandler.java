package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.formedix.currency.rate.finder.parsers.Conversions.calculateAverage;
import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;
import static java.math.BigDecimal.ZERO;

public class AverageRateForSpecifiedCurrencyAndPeriodHandler extends RateHandler<BigDecimal>
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
                BigDecimal sum = getSumOfRates(rates);
                return calculateAverage.apply(new BigDecimal(rates.size()), sum);
            }
        }
        return ZERO;
    }

    private BigDecimal getSumOfRates(List<CurrencyData> rates)
    {
        return rates.stream()
                    .map(x -> x.getRate()
                               .get())
                    .reduce(ZERO, BigDecimal::add);
    }
}
