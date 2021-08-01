package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.test.common.CommonTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.formedix.currency.rate.finder.parsers.Conversions.calculateAverage;
import static com.formedix.currency.rate.finder.parsers.Conversions.convertToBigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class AverageRateForSpecifiedCurrencyAndPeriodHandlerTest extends CommonTestData
{
    private RateHandler<BigDecimal> testee;

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
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        List<String> testRates = new ArrayList<>(asList("N/A", "N/A", "N/A"));
        List<CurrencyData> rates = createList(sourceCurrency, testRates);
        BigDecimal expectedResult = calculateExpected(rates);

        assertNotNull(result);
        assertEquals(ZERO, result);
    }

    private BigDecimal calculateExpected(List<CurrencyData> rates)
    {
        BigDecimal sum = rates.stream()
                              .map(x -> x.getRate()
                                         .get())
                              .reduce(BigDecimal.ZERO, BigDecimal::add);

        return calculateAverage.apply(new BigDecimal(rates.size()), sum);
    }

    @Test
    void getsAverageRateForSpecifiedPeriodWhereAllHaveRateValues() throws InvalidDateFormatException
    {
        //GIVEN
        final String startDate = "2021-07-22";
        final String endDate = "2021-07-26";
        final String sourceCurrency = "JPY";

        //WHEN
        BigDecimal result = testee.handleRequest(new RequestData(referenceData, startDate, endDate, sourceCurrency));

        //THEN
        List<String> testRates = new ArrayList<>(asList("130.05", "130.11", "129.83"));
        List<CurrencyData> rates = createList(sourceCurrency, testRates);
        BigDecimal expectedResult = calculateExpected(rates);

        assertNotNull(result);
        assertNotEquals(ZERO, result);
        assertEquals(expectedResult, result);
    }


    private List<CurrencyData> createList(String sourceCurrency, List<String> testRates)
    {
        List<CurrencyData> rates = new ArrayList<>();
        testRates.forEach(rate -> rates.add(new CurrencyData(sourceCurrency, convertToBigDecimal.apply(rate))));
        return rates;
    }
}