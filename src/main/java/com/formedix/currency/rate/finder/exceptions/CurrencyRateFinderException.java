package com.formedix.currency.rate.finder.exceptions;

public class CurrencyRateFinderException extends Exception
{
    public CurrencyRateFinderException(final String msg, final Throwable throwable)
    {
        super(msg, throwable);
    }

    public CurrencyRateFinderException(final String msg)
    {
        super(msg);
    }
}
