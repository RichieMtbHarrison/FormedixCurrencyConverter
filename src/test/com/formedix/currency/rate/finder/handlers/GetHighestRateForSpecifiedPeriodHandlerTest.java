package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.formedix.currency.rate.finder.constants.Constants.MINUS_ONE_DOUBLE;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

class GetHighestRateForSpecifiedPeriodHandlerTest extends CommonTestData
{
    private RateHandler<Double> testee;

    @BeforeEach
    void setUp()
    {
        testee = new GetHighestRateForSpecifiedPeriodHandler();
    }

    @Test
    void getsMinusOneResultForOutOfRangeDate() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-06-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        Optional<Double> expectedResult = of(130.11D);
        assertNotNull(result);
        assertEquals(MINUS_ONE_DOUBLE, result);
    }

    @Test
    void getsHighestRateForSpecifiedPeriod() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        Double result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        Optional<Double> expectedResult = of(130.11D);
        assertNotNull(result);
        assertNotEquals(MINUS_ONE_DOUBLE, result);
        assertEquals(expectedResult.get(), result);
    }
}