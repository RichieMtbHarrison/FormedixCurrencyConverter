package com.formedix.currency.rate.finder.impl;

import com.formedix.currency.rate.finder.exceptions.CurrencyRateFinderException;
import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.handlers.*;
import com.formedix.currency.rate.finder.interfaces.CurrencyRateFinder;
import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;
import com.formedix.currency.rate.finder.parsers.FileParser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CurrencyRateFinderImpl implements CurrencyRateFinder
{
    private final Map<LocalDate, List<CurrencyData>> referenceData;
    private final RateHandler<Double> calculateExchangeAmountHandler;
    private final RateHandler<Optional<List<CurrencyData>>> getAllRatesForGivenDateHandler;
    private final RateHandler<Double> getHighestRateForSpecifiedPeriodHandler;
    private final RateHandler<Double> averageRateForSpecifiedCurrencyAndPeriodHandler;

    /**
     * @param dataSource A .csv File System data source, E.G. C:\my_resources\myfile.csv
     * @throws CurrencyRateFinderException If the resource cannot be found or there is an error in reading the file contents.
     */
    public CurrencyRateFinderImpl(final String dataSource) throws CurrencyRateFinderException
    {
        FileParser fileParser = new FileParser();
        referenceData = fileParser.parse(dataSource);
        if (0 == referenceData.size()) {
            final String errorMsg = "No data has successfully been read for the supplied data source, please check source.";
            throw new CurrencyRateFinderException(errorMsg);
        }
        
        getAllRatesForGivenDateHandler = new GetAllRatesForGivenDateHandler();
        calculateExchangeAmountHandler = new CalculateExchangeAmountHandler();
        getHighestRateForSpecifiedPeriodHandler = new GetHighestRateForSpecifiedPeriodHandler();
        averageRateForSpecifiedCurrencyAndPeriodHandler = new AverageRateForSpecifiedCurrencyAndPeriodHandler();
    }

    @Override
    public Optional<List<CurrencyData>> getAllRatesForGivenDate(final String date) throws InvalidDateFormatException
    {
        return getAllRatesForGivenDateHandler.handleRequest(new RequestData(referenceData, date));
    }

    @Override
    public Double calculateExchangeAmountForGivenDate(final String date, final String sourceCurrency,
                                                      final String targetCurrency,
                                                      final String amount) throws InvalidDateFormatException
    {
        return calculateExchangeAmountHandler.handleRequest(new RequestData(referenceData, date, sourceCurrency,
                targetCurrency, amount));
    }

    @Override
    public Double getHighestRateForSpecifiedPeriod(final String startDate, final String endDate,
                                                   final String sourceCurrency) throws InvalidDateFormatException
    {
        return getHighestRateForSpecifiedPeriodHandler.handleRequest(new RequestData(referenceData, startDate,
                endDate, sourceCurrency));
    }

    @Override
    public Double getAverageRateForSpecifiedCurrencyAndPeriod(final String startDate, final String endDate,
                                                              final String sourceCurrency) throws InvalidDateFormatException
    {
        return averageRateForSpecifiedCurrencyAndPeriodHandler.handleRequest(new RequestData(referenceData, startDate,
                endDate, sourceCurrency));
    }
}
