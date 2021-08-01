package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.exceptions.InvalidDateFormatException;
import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.formedix.currency.rate.finder.constants.Constants.FORMATTER_PATTERN;
import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;
import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableMap;

public abstract class RateHandler<T>
{
    public T handleRequest(RequestData requestData) throws InvalidDateFormatException
    {
        final String suppliedDateErrorMessage = "The supplied date %s is not in the format of %s.";
        try {
            return getResult(requestData);
        } catch (
                DateTimeParseException ex) {
            throw new InvalidDateFormatException(format(suppliedDateErrorMessage, requestData.getDateRange1(),
                    FORMATTER_PATTERN), ex);
        }
    }

    protected abstract T getResult(RequestData requestData);

    protected void addRateForSpecifiedCurrency(final List<CurrencyData> rates,
                                               final List<CurrencyData> listOfCurrencyData,
                                               final RequestData requestData)
    {
        Optional<CurrencyData> currencyDataFound = listOfCurrencyData.stream()
                                                                     .filter(currencyData -> currencyData.getCurrency()
                                                                                                         .equalsIgnoreCase(requestData.getSourceCurrency()))
                                                                     .findFirst();
        currencyDataFound.ifPresent(rates::add);
    }

    protected boolean datesSuppliedAreInDataRange(RequestData requestData)
    {
        return requestData.getReferenceData()
                          .containsKey(getLocalDateFromString.apply(requestData.getDateRange1()))
                && requestData.getReferenceData()
                              .containsKey(getLocalDateFromString.apply(requestData.getDateRange2()));
    }

    protected Map<LocalDate, List<CurrencyData>> getPeriodsInRange(final RequestData requestData,
                                                                   final LocalDate startDate,
                                                                   final LocalDate endDate)
    {
        return requestData.getReferenceData()
                          .entrySet()
                          .stream()
                          .filter(mapRecord -> isInRange(mapRecord.getKey(), startDate, endDate))
                          .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected boolean isInRange(final LocalDate mapRecordDate, final LocalDate startDate, final LocalDate endDate)
    {
        boolean result =
                (mapRecordDate.isEqual(startDate) || mapRecordDate.isAfter(startDate) && mapRecordDate.isBefore(endDate))
                        || (mapRecordDate.isEqual(endDate) || mapRecordDate.isBefore(endDate) && mapRecordDate.isAfter(startDate));

        return result;
    }
}
