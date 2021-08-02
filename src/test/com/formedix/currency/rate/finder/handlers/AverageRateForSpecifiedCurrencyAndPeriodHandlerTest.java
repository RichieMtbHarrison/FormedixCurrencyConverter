package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static org.junit.jupiter.api.Assertions.*;

public class AverageRateForSpecifiedCurrencyAndPeriodHandlerTest extends CommonTestData
{
    private RateHandler<Double> testee;

    @BeforeEach
    void setUp()
    {
        testee = new AverageRateForSpecifiedCurrencyAndPeriodHandler();
    }


    @Test
    void getsAverageRateForSpecifiedPeriodWhereAllHaveNoRateValues() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "CYP";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        assertNotNull(result);
        assertEquals(MINUS_ONE_DOUBLE, result);
    }

    @Test
    void getsAverageRateForSpecifiedPeriodWhereAllHaveRateValues() throws InvalidDateFormatException
    {
        //GIVEN
        //Values for period are : "130.05", "130.11", "129.83";
        final String startDate = "2021-07-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        Double expectedResult = 389.99D / 3D;

        assertNotNull(result);
        assertNotEquals(MINUS_ONE_DOUBLE, result);
        assertEquals(expectedResult, result);
    }
}