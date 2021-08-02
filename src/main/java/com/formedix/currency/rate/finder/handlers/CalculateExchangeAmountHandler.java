package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static com.formedix.currency.rate.finder.parsers.Conversions.*;
import static java.util.Optional.ofNullable;

public class CalculateExchangeAmountHandler extends RateHandler<Double>
{
    protected Double getResult(final RequestData requestData)
    {
        final LocalDate periodToFind = getLocalDateFromString.apply(requestData.getDateRange1());
        Optional<List<CurrencyData>> periodData = ofNullable(requestData.getReferenceData()
                                                                        .get(periodToFind));
        if (periodData.isPresent()) {
            Double sourceCurrencyRate = getRateForSpecifiedCurrency(requestData.getSourceCurrency(), periodData.get());
            Double targetCurrencyRate = getRateForSpecifiedCurrency(requestData.getTargetCurrency(), periodData.get());

            //Both currency rates need to be present, remember for some currencies there may not have been an
            // available rate on that day.
            if (bothCurrencyRatesValid.apply(sourceCurrencyRate, targetCurrencyRate)) {
                Double amountToConvert = convertToDouble.apply(requestData.getAmount());

                return multiplyRateValueByAmount.apply(divideTwoRates.apply(targetCurrencyRate,
                        sourceCurrencyRate), amountToConvert);
            }
        }
        return MINUS_ONE_DOUBLE;
    }

    private final BiFunction<Double, Double, Boolean> bothCurrencyRatesValid =
            (sourceCurrencyRate, targetCurrencyRate) -> (null != sourceCurrencyRate && null != targetCurrencyRate)
                    && (sourceCurrencyRate
                    .compareTo(MINUS_ONE_DOUBLE) > 0 &&
                    targetCurrencyRate
                            .compareTo(MINUS_ONE_DOUBLE) > 0);

    private Double getRateForSpecifiedCurrency(final String currencyToFind,
                                               final List<CurrencyData> periodData)
    {
        Optional<CurrencyData> data = periodData
                .stream()
                .filter(currency -> currency
                        .getCurrency()
                        .equals(currencyToFind))
                .findFirst();

        return data.isPresent()
                ? data.get()
                      .getRate()
                : MINUS_ONE_DOUBLE;
    }
}
