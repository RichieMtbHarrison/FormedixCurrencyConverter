package com.formedix.currency.rate.finder.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RequestData
{
    private final Map<LocalDate, List<CurrencyData>> referenceData;
    private String dateRange1;
    private String dateRange2;
    private String sourceCurrency;
    private String targetCurrency;
    private String amount;

    public final Map<LocalDate, List<CurrencyData>> getReferenceData()
    {
        return referenceData;
    }

    public final String getDateRange1()
    {
        return dateRange1;
    }

    public final String getDateRange2()
    {
        return dateRange2;
    }

    public final String getSourceCurrency()
    {
        return sourceCurrency;
    }

    public final String getTargetCurrency()
    {
        return targetCurrency;
    }

    public final String getAmount()
    {
        return amount;
    }

    public RequestData(final Map<LocalDate, List<CurrencyData>> referenceData, final String dateRange1)
    {
        this.referenceData = referenceData;
        this.dateRange1 = dateRange1;
    }

    public RequestData(final Map<LocalDate, List<CurrencyData>> referenceData, final String dateRange1,
                       final String sourceCurrency, final String targetCurrency, final String amount)
    {
        this(referenceData, dateRange1);
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    public RequestData(Map<LocalDate, List<CurrencyData>> referenceData, String dateRange1, String dateRange2,
                       String sourceCurrency)
    {
        this(referenceData, dateRange1);
        this.dateRange2 = dateRange2;
        this.sourceCurrency = sourceCurrency;
    }
}
