package com.formedix.currency.rate.finder.handlers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import com.formedix.currency.rate.finder.models.RequestData;

import java.util.List;
import java.util.Optional;

import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;
import static java.util.Optional.ofNullable;

public class GetAllRatesForGivenDateHandler extends RateHandler<Optional<List<CurrencyData>>>
{
    @Override
    protected Optional<List<CurrencyData>> getResult(final RequestData requestData)
    {
        return ofNullable(requestData.getReferenceData()
                                     .get(getLocalDateFromString.apply(requestData.getDateRange1())));
    }
}
