package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static com.formedix.currency.rate.finder.parsers.Conversions.calculateAverage;
import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;

public class AverageRateForSpecifiedCurrencyAndPeriodHandler extends RateHandler<Double>
{
    @Override
    protected Double getResult(final RequestData requestData)
    {
        if (datesSuppliedAreInDataRange(requestData)) {
            final LocalDate startDate = getLocalDateFromString.apply(requestData.getDateRange1());
            final LocalDate endDate = getLocalDateFromString.apply(requestData.getDateRange2());
            Map<LocalDate, List<CurrencyData>> dataInRange = getPeriodsInRange(requestData, startDate, endDate);
            if (dataInRange.size() > 0) {
                List<CurrencyData> rates = new ArrayList<>();
                dataInRange.forEach((key, value) -> addRateForSpecifiedCurrency(rates, dataInRange.get(key),
                        requestData));
                Double sum = getSumOfRates(rates);
                return sum.equals(0.0D) ? MINUS_ONE_DOUBLE : calculateAverage.apply((double) rates.size(), sum);
            }
        }
        return MINUS_ONE_DOUBLE;
    }
    
    private Double getSumOfRates(List<CurrencyData> rates)
    {
        return rates.stream()
                    .filter(cd -> cd.getRate() != MINUS_ONE_DOUBLE)
                    .mapToDouble(CurrencyData::getRate)
                    .sum();
    }
}
