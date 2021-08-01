package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.formedix.currency.rate.finder.parsers.Conversions.*;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;

class CalculateExchangeAmountHandlerTest extends CommonTestData
{
    private RateHandler<BigDecimal> testee;

    private static final String DATE_PERIOD = "2021-07-27";
    private static final String AMOUNT = "1000";

    @BeforeEach
    void setUp()
    {
        testee = new CalculateExchangeAmountHandler();
    }

    @Test
    void convertAmountForSpecifiedDateWhereDateIsInvalidFormat()
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String targetCurrency = "GBP";
        final String DATE_PERIOD = "27-07-2021";

        //WHEN / THEN
        assertThrows(InvalidDateFormatException.class, () -> testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency, targetCurrency, AMOUNT)));
    }

    @Test
    void convertAmountForSpecifiedDateWhereACurrencyIsNotValid() throws InvalidDateFormatException
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String invalidTargetCurrency = "CHR";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency, invalidTargetCurrency, AMOUNT));

        //THEN
        assertNotNull(result);
        assertEquals(result, ZERO);
    }

    @Test
    void convertAmountForSpecifiedDateWhereACurrencyRateIsNotApplicable() throws InvalidDateFormatException
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String notApplicableTargetCurrency = "CYP";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency, notApplicableTargetCurrency, AMOUNT));

        //THEN
        assertNotNull(result);
        assertEquals(result, ZERO);
    }

    @Test
    void convertAmountForSpecifiedDateAndValidCurrencies() throws InvalidDateFormatException
    {
        //GIVEN
        final String targetExchangeRate = "0.85503";
        final String sourceExchangeRate = "129.98";

        Optional<BigDecimal> seRate = convertToBigDecimal.apply(sourceExchangeRate);
        Optional<BigDecimal> teRate = convertToBigDecimal.apply(targetExchangeRate);
        Optional<BigDecimal> sbdAmount = convertToBigDecimal.apply(AMOUNT);
        BigDecimal expectedResult = multiplyRateValueByAmount.apply(divideTwoRates.apply(teRate.get(), seRate.get()), sbdAmount.get());

        final String sourceCurrency = "JPY";
        final String targetCurrency = "GBP";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency, targetCurrency, AMOUNT));

        //THEN
        assertNotNull(result);
        assertEquals(result, expectedResult);
    }
}