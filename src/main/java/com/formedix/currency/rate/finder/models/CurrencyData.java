package com.formedix.currency.rate.finder.models;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

public final class CurrencyData
{
    private final String currency;
    private final Optional<BigDecimal> rate;

    public String getCurrency()
    {
        return currency;
    }

    public Optional<BigDecimal> getRate()
    {
        return rate;
    }

    public CurrencyData(final String currency, Optional<BigDecimal> rate)
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
        if (rateToCompare.getRate()
                         .isPresent() && this.rate.isPresent()) {
            return currenciesMatch.apply(rateToCompare) && ratesMatch.apply(rateToCompare);
        } else {
            return rateToCompare.getCurrency()
                                .equals(this.getCurrency());
        }
    }

    @Override
    public int hashCode()
    {
        int result = 9;
        if (null != currency) {
            result = 18 * result + currency.hashCode();
        }
        if (rate.isPresent()) {
            result = 15 * result + rate.hashCode();
        }
        return result;
    }

    private final Function<CurrencyData, Boolean> currenciesMatch = (reference) -> reference.getCurrency()
                                                                                            .equalsIgnoreCase(this.getCurrency());
    private final Function<CurrencyData, Boolean> ratesMatch = (reference) -> reference.getRate()
                                                                                       .get()
                                                                                       .equals(this.getRate()
                                                                                                   .get());
}
