package com.formedix.currency.rate.finder.parsers;

import com.formedix.currency.rate.finder.models.CurrencyData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.formedix.currency.rate.finder.constants.Constants.DATE_FORMATTER;
import static java.time.LocalDate.parse;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public final class Conversions
{
    private static final String RATE_FORMAT = "\\d+(\\.\\d+)?";

    public static final Function<String, Optional<BigDecimal>> convertToBigDecimal = rate -> rate.matches(RATE_FORMAT) ? of(new BigDecimal(rate)) : empty();

    public static final Function<String, LocalDate> getRecordDate = (date) -> parse(date, DATE_FORMATTER);

    public static final BiFunction<CurrencyData, CurrencyData, Boolean> currencyMatches = (expected, actual) -> expected.equals(actual);
}
