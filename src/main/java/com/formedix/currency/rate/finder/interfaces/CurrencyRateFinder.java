package com.formedix.currency.rate.finder.interfaces;

import com.formedix.currency.rate.finder.exceptions.CurrencyRateFinderException;
import com.formedix.currency.rate.finder.models.CurrencyData;

import java.util.List;
import java.util.Optional;

public interface CurrencyRateFinder
{
    /**
     * @param date A single date period in which to search, format required "yyyy-MM-dd"
     * @return If the date provided is in range then returns an Immutable list of Currencies and associated rates otherwise an empty Optional
     * @throws CurrencyRateFinderException If date provided is not of correct format.
     */
    Optional<List<CurrencyData>> getAllRatesForGivenDate(final String date) throws CurrencyRateFinderException;

    /**
     * @param date           A single date period in which to search, format required "yyyy-MM-dd"
     * @param sourceCurrency The currency you wish to convert from.
     * @param targetCurrency The currency you wish to convert to.
     * @param amount         The amount to be converted.
     * @return The amount as calculated against the two currency rates. if a currency supplied is not in the valid range or the date is not of the
     * correct format "yyyy-MM-dd" then a value of -1D will be returned.
     * @throws CurrencyRateFinderException If date provided is not of correct format.
     */
    Double calculateExchangeAmountForGivenDate(final String date, final String sourceCurrency,
                                               final String targetCurrency,
                                               final String amount) throws CurrencyRateFinderException;

    /**
     * @param startDate      The inclusive date at which you wish to start the search, format required "yyyy-MM-dd"
     * @param endDate        The inclusive date at which you wish to end the search, format required "yyyy-MM-dd"
     * @param sourceCurrency The currency you wish to find the highest rate for, E.G. JPY, GBP
     * @return The highest rate as found for the range of dates provided. if a currency supplied is not in the valid range or the date is not of the
     * correct format "yyyy-MM-dd" then a value of -1D will be returned.
     * @throws CurrencyRateFinderException If date provided is not of correct format.
     */
    Double getHighestRateForSpecifiedPeriod(final String startDate, final String endDate,
                                            final String sourceCurrency) throws CurrencyRateFinderException;

    /**
     * @param startDate      The inclusive date at which you wish to start the search, format required "yyyy-MM-dd"
     * @param endDate        The inclusive date at which you wish to end the search, format required "yyyy-MM-dd"
     * @param sourceCurrency The currency you wish to find the highest rate for, E.G. JPY, GBP
     * @return The average for all rates found where a rate was supplied for that date for the given currency, rates specified as N/A are ignored.
     * If all rates for a given currency were N/A then a value of -1D will be returned
     * @throws CurrencyRateFinderException If date provided is not of correct format.
     */
    Double getAverageRateForSpecifiedCurrencyAndPeriod(final String startDate, final String endDate,
                                                       final String sourceCurrency) throws CurrencyRateFinderException;
}
