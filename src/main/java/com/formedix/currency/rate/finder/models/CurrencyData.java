package com.formedix.currency.rate.finder.models;

import java.util.function.Function;

public final class CurrencyData
{
    private final String currency;
    private final Double rate;

    public String getCurrency()
    {
        return currency;
    }

    public Double getRate()
    {
        return rate;
    }

    public CurrencyData(final String currency, Double rate)
    {
        this.currency = currency;
        this.rate = rate;
    }

    @Override
    public boolean equals(Object other)
    {
        if (null == other || other.getClass() != this.getClass()) {
            return false;
        }
        CurrencyData rateToCompare = (CurrencyData) other;
        return currenciesMatch.apply(rateToCompare) && ratesMatch.apply(rateToCompare);
    }

    @Override
    public int hashCode()
    {
        int result = 9;
        if (null != currency) {
            result = 18 * result + currency.hashCode();
        }
        if (null != rate) {
            result = 15 * result + rate.hashCode();
        }
        return result;
    }

    private final Function<CurrencyData, Boolean> currenciesMatch = (reference) -> reference.getCurrency()
                                                                                            .equalsIgnoreCase(this.getCurrency());

    private final Function<CurrencyData, Boolean> ratesMatch = (reference) -> reference.getRate()
                                                                                       .equals(this.getRate());
}
