package com.formedix.currency.rate.finder.models;

import org.junit.jupiter.api.Test;

import static com.formedix.currency.rate.finder.parsers.Conversions.currenciesMatch;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyDataTest
{
    @Test
    public void referenceRatesMatch()
    {
        //GIVEN
        String currency = "USD";
        Double rate = 1.181D;
        CurrencyData expected = new CurrencyData(currency, rate);
        CurrencyData actual = new CurrencyData(currency, rate);

        //WHEN /THEN
        assertTrue(currenciesMatch.apply(expected, actual));
    }

    @Test
    public void referenceRatesDoNotMatchWhenRateNotSame()
    {
        //GIVEN
        String currency = "USD";
        Double rate1 = 1.181D;
        Double rate2 = 1.112D;

        CurrencyData expected = new CurrencyData(currency, rate1);
        CurrencyData actual = new CurrencyData(currency, rate2);

        //WHEN /THEN
        assertFalse(currenciesMatch.apply(expected, actual));
    }

    @Test
    public void referenceRatesDoNotMatchWhenCurrencyNotSame()
    {
        //GIVEN
        String currency1 = "USD";
        String currency2 = "JPY";
        Double rate = 1.181D;

        CurrencyData expected = new CurrencyData(currency1, rate);
        CurrencyData actual = new CurrencyData(currency2, rate);

        //WHEN /THEN
        assertFalse(currenciesMatch.apply(expected, actual));
    }
}