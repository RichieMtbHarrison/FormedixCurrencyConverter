package com.formedix.currency.rate.finder.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.formedix.currency.rate.finder.parsers.Conversions.convertToBigDecimal;
import static com.formedix.currency.rate.finder.parsers.Conversions.currencyMatches;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyDataTest
{
    @Test
    public void referenceRatesMatch()
    {
        //GIVEN
        String currency = "USD";
        Optional<BigDecimal> rate = convertToBigDecimal.apply("1.181");
        CurrencyData expected = new CurrencyData(currency, rate);
        CurrencyData actual = new CurrencyData(currency, rate);

        //WHEN /THEN
        assertTrue(currencyMatches.apply(expected, actual));
    }

    @Test
    public void referenceRatesDoNotMatchWhenRateNotSame()
    {
        //GIVEN
        String currency = "USD";
        Optional<BigDecimal> rate1 = convertToBigDecimal.apply("1.181");
        Optional<BigDecimal> rate2 = convertToBigDecimal.apply("1.112");

        CurrencyData expected = new CurrencyData(currency, rate1);
        CurrencyData actual = new CurrencyData(currency, rate2);

        //WHEN /THEN
        assertFalse(currencyMatches.apply(expected, actual));
    }

    @Test
    public void referenceRatesDoNotMatchWhenCurrencyNotSame()
    {
        //GIVEN
        String currency1 = "USD";
        String currency2 = "JPY";
        Optional<BigDecimal> rate = convertToBigDecimal.apply("1.181");

        CurrencyData expected = new CurrencyData(currency1, rate);
        CurrencyData actual = new CurrencyData(currency2, rate);

        //WHEN /THEN
        assertFalse(currencyMatches.apply(expected, actual));
    }
}