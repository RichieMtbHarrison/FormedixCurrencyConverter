package com.formedix.currency.rate.finder.parsers;

import com.formedix.currency.rate.finder.models.CurrencyData;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.formedix.currency.rate.finder.constants.Constants.*;
import static java.lang.Double.parseDouble;
import static java.time.LocalDate.parse;

public final class Conversions
{
    public static final Function<String, Double> convertToDouble = rate -> rate.matches(RATE_FORMAT)
            ? parseDouble(rate)
            : MINUS_ONE_DOUBLE;

    public static final BiFunction<CurrencyData, CurrencyData, Boolean> currenciesMatch = CurrencyData::equals;

    public static final Function<String, LocalDate> getLocalDateFromString = (date) -> parse(date, DATE_FORMATTER);

    public static final BiFunction<Double, Double, Double> divideTwoRates = (targetRate, sourceRate) -> targetRate / sourceRate;

    public static final BiFunction<Double, Double, Double> multiplyRateValueByAmount = (rate, amount) -> rate * amount;

    public static final BiFunction<Double, Double, Double> calculateAverage = (numberOfItems, sumOfItems) -> sumOfItems / numberOfItems;
}

