package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.formedix.currency.rate.finder.parsers.Conversions.currenciesMatch;
import static org.junit.jupiter.api.Assertions.*;

class GetAllRatesForGivenDateHandlerTest extends CommonTestData
{
    private RateHandler<Optional<List<CurrencyData>>> testee;

    @BeforeEach
    void setUp()
    {
        testee = new GetAllRatesForGivenDateHandler();
    }

    @Test
    void getsReferenceRatesForSpecifiedDate() throws InvalidDateFormatException
    {
        //GIVEN
        final String date = "2021-07-27";

        //WHEN
        Optional<List<CurrencyData>> result = testee.handleRequest(new RequestData(referenceData, date));

        //THEN
        assertTrue(result.isPresent());
        assertEquals(41, result.get()
                               .size());
        result.get()
              .forEach(rate -> matchesExpected(expectedRates1, rate));
    }

    @Test
    void attemptToGetReferenceRatesOutsideOfDateRage() throws InvalidDateFormatException
    {
        //GIVEN
        final String date = "2021-07-17";

        //WHEN
        Optional<List<CurrencyData>> result = testee.handleRequest(new RequestData(referenceData, date));

        //THEN
        assertFalse(result.isPresent());
    }

    private void matchesExpected(List<CurrencyData> expectedRates, CurrencyData rate)
    {
        Optional<CurrencyData> o = expectedRates.stream()
                                                .filter(expectedRate -> rate.getCurrency()
                                                                            .equals(expectedRate.getCurrency()))
                                                .findFirst();
        assertTrue(o.isPresent());
        assertTrue(currenciesMatch.apply(o.get(), rate));
    }

}