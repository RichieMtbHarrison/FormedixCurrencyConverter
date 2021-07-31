package com.formedix.currency.rate.finder.exceptions;

public class InvalidDateFormatException extends CurrencyRateFinderException
{
    public InvalidDateFormatException(final String msg, final Throwable throwable)
    {
        super(msg, throwable);
    }
}
