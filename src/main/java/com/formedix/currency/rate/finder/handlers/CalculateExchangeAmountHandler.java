package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.formedix.currency.rate.finder.parsers.Conversions.*;
import static java.math.BigDecimal.ZERO;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class CalculateExchangeAmountHandler extends RateHandler<BigDecimal>
{
    protected BigDecimal getResult(final RequestData requestData)
    {
        final LocalDate periodToFind = getLocalDateFromString.apply(requestData.getDateRange1());
        Optional<List<CurrencyData>> periodData = ofNullable(requestData.getReferenceData()
                                                                        .get(periodToFind));
        if (periodData.isPresent()) {
            Optional<BigDecimal> sourceCurrencyRate = getRateForSpecifiedCurrency(requestData.getSourceCurrency(), periodData.get());
            Optional<BigDecimal> targetCurrencyRate = getRateForSpecifiedCurrency(requestData.getTargetCurrency(), periodData.get());
            //Both currency rates need to be present, remember for some currencies there may not have been an available rate on that day.
            if (bothCurrencyRatesPresent.apply(sourceCurrencyRate, targetCurrencyRate)) {
                Optional<BigDecimal> amountToConvert = convertToBigDecimal.apply(requestData.getAmount());
                return multiplyRateValueByAmount.apply(divideTwoRates.apply(targetCurrencyRate.get(), sourceCurrencyRate.get()), amountToConvert.get());
            }
        }
        return ZERO;
    }

    private final BiFunction<Optional<BigDecimal>, Optional<BigDecimal>, Boolean> bothCurrencyRatesPresent = (sourceCurrencyRate, targetCurrencyRate) -> sourceCurrencyRate.isPresent() && targetCurrencyRate.isPresent();

    private Optional<BigDecimal> getRateForSpecifiedCurrency(final String currencyToFind,
                                                             final List<CurrencyData> periodData)
    {
        Optional<CurrencyData> data = periodData
                .stream()
                .filter(currency -> currency.getCurrency()
                                            .equals(currencyToFind))
                .findFirst();

        return data.isPresent() ? data.get()
                                      .getRate() : empty();
    }
}
