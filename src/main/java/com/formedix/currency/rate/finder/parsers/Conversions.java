package com.formedix.currency.rate.finder.parsers;

import com.formedix.currency.rate.finder.models.CurrencyData;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.formedix.currency.rate.finder.constants.Constants.DATE_FORMATTER;
import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDate.parse;
import static java.util.Optional.of;

public final class Conversions
{
    private static final String RATE_FORMAT = "\\d+(\\.\\d+)?";

    public static final BiFunction<BigDecimal, BigDecimal, BigDecimal> divideTwoRates =
            (targetRate, sourceRate) -> targetRate.divide(sourceRate, new MathContext(5, HALF_UP));

    public static final BiFunction<BigDecimal, BigDecimal, BigDecimal> multiplyRateValueByAmount =
            (rate, amount) -> rate.multiply(amount, new MathContext(5, HALF_UP));

    public static final Function<String, Optional<BigDecimal>> convertToBigDecimal =
            rate -> rate.matches(RATE_FORMAT) ? of(new BigDecimal(rate)) : of(new BigDecimal(0));

    public static final Function<String, LocalDate> getLocalDateFromString = (date) -> parse(date, DATE_FORMATTER);

    public static final BiFunction<CurrencyData, CurrencyData, Boolean> currencyMatches = CurrencyData::equals;

    public static final BiFunction<BigDecimal, BigDecimal, BigDecimal> calculateAverage =
            (numberOfItems, sumOfItems) -> sumOfItems.divide(numberOfItems, new MathContext(5,
                    HALF_UP));
}
