package com.formedix.currency.rate.finder.impl;

import com.formedix.currency.rate.finder.exceptions.CurrencyRateFinderException;
import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static org.junit.jupiter.api.Assertions.*;


class CurrencyConverterImplTest extends CommonTestData
{
    private CurrencyRateFinderImpl testee;

    @BeforeEach
    void setUp() throws CurrencyRateFinderException
    {
        testee = new CurrencyRateFinderImpl(VALID_TEST_RESOURCE);
    }

    @Test
    void getAverageRateForSpecifiedCurrencyAndPeriod() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-23";
        final String endDate = "2021-07-27";
        final String sourceCurrency = "JPY";

        //WHEN
        Double result = testee.getAverageRateForSpecifiedCurrencyAndPeriod(startDate, endDate, sourceCurrency);

        //THEN
        Double expectedResult = 130.04666666666665D;
        assertNotNull(result);
        assertNotEquals(MINUS_ONE_DOUBLE, result);
        assertEquals(expectedResult, result);
    }

    @Test
    void getHighestRateForSpecifiedCurrencyAndPeriod() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-23";
        final String endDate = "2021-07-27";
        final String sourceCurrency = "JPY";

        //WHEN
        Double result = testee.getHighestRateForSpecifiedPeriod(startDate, endDate, sourceCurrency);

        //THEN
        Double expectedResult = 130.11D;
        assertNotNull(result);
        assertNotEquals(MINUS_ONE_DOUBLE, result);
        assertEquals(expectedResult, result);
    }

    @Test
    void getAllRatesForGivenDate() throws InvalidDateFormatException
    {
        //GIVEN
        final String date = "2021-07-27";
        //WHEN
        Optional<List<CurrencyData>> result = testee.getAllRatesForGivenDate(date);
        //THEN
        assertTrue(result.isPresent());
        assertEquals(41, result.get()
                               .size());
    }

    @Test
    void convertAmountForSpecifiedDateAndValidCurrencies() throws InvalidDateFormatException
    {
        //GIVEN
        final String date = "2021-07-27";
        final String sourceCurrency = "JPY";
        final String targetCurrency = "GBP";
        final String amount = "1000";

        //WHEN
        Double result = testee.calculateExchangeAmountForGivenDate(date, sourceCurrency, targetCurrency, amount);

        //THEN
        assertNotNull(result);
        assertNotEquals(MINUS_ONE_DOUBLE, result);
    }
}