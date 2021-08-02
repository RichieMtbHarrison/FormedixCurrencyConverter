package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static java.lang.Double.valueOf;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

class CalculateExchangeAmountHandlerTest extends CommonTestData
{
    private RateHandler<Double> testee;

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
        assertThrows(InvalidDateFormatException.class, () -> testee.handleRequest(new RequestData(referenceData,
                DATE_PERIOD, sourceCurrency, targetCurrency, AMOUNT)));
    }

    @Test
    void convertAmountForSpecifiedDateWhereACurrencyIsNotValid() throws InvalidDateFormatException
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String invalidTargetCurrency = "CHR";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency,
                invalidTargetCurrency, AMOUNT));

        //THEN
        assertNotNull(result);
        assertEquals(MINUS_ONE_DOUBLE, result);
    }

    @Test
    void convertAmountForSpecifiedDateWhereACurrencyRateIsNotApplicable() throws InvalidDateFormatException
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String notApplicableTargetCurrency = "CYP";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency,
                notApplicableTargetCurrency, AMOUNT));

        //THEN
        assertNotNull(result);
        assertEquals(MINUS_ONE_DOUBLE, result);
    }

    @Test
    void convertAmountForSpecifiedDateAndValidCurrencies() throws InvalidDateFormatException
    {
        //GIVEN
        final String sourceCurrency = "JPY";
        final String targetCurrency = "GBP";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, DATE_PERIOD, sourceCurrency,
                targetCurrency, AMOUNT));

        //THEN
        final double targetExchangeRate = 0.85503D;
        final double sourceExchangeRate = 129.98D;
        
        Optional<Double> seRate = of(sourceExchangeRate);
        Optional<Double> teRate = of(targetExchangeRate);
        Optional<Double> sbdAmount = of(valueOf(AMOUNT));
        Double expectedResult = (teRate.get() / seRate.get()) * sbdAmount.get();

        assertNotNull(result);
        assertEquals(result, expectedResult);
    }
}