package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.formedix.currency.rate.finder.parsers.Conversions.convertToBigDecimal;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;

class GetHighestRateForSpecifiedPeriodHandlerTest extends CommonTestData
{
    private RateHandler<BigDecimal> testee;

    @BeforeEach
    void setUp()
    {
        testee = new GetHighestRateForSpecifiedPeriodHandler();
    }

    @Test
    void getsZeroResultForOutOfRangeDate() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-06-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        Optional<BigDecimal> expectedResult = convertToBigDecimal.apply("130.11");
        assertNotNull(result);
        assertEquals(ZERO, result);
    }

    @Test
    void getsHighestRateForSpecifiedPeriod() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        Optional<BigDecimal> expectedResult = convertToBigDecimal.apply("130.11");
        assertNotNull(result);
        assertNotEquals(ZERO, result);
        assertEquals(expectedResult.get(), result);
    }
}