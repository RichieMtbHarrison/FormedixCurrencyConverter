package com.formedix.currency.rate.finder.test.common;

import com.formedix.currency.rate.finder.models.CurrencyData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.formedix.currency.rate.finder.constants.Constants.COMMA_DELIMITER;
import static com.formedix.currency.rate.finder.parsers.Conversions.convertToDouble;
import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;

public abstract class CommonTestData
{
    private static final String currencyInputs = "USD,JPY,BGN,CYP,CZK,DKK,EEK,GBP,HUF,LTL,LVL,MTL,PLN,ROL,RON,SEK," +
            "SIT,SKK,CHF,ISK,NOK,HRK,RUB,TRL,TRY,AUD,BRL,CAD,CNY,HKD,IDR,ILS,INR,KRW,MXN,MYR,NZD,PHP,SGD,THB,ZAR";
    private static final String[] currencies = currencyInputs.split(",");

    private static final String inputRates1 = "1.181,129.98,1.9558,N/A,25.685,7.4371,N/A,0.85503,359.43,N/A,N/A,N/A,4" +
            ".5906,N/A,4.9196,10.165,N/A,N/A,1.0806,149,10.4423,7.5064,87.0133,N/A,10.107,1.6029,6.1288,1.4839,7.6799,9" +
            ".1915,17109.52,3.8411,87.913,1360.75,23.6687,4.999,1.6956,59.553,1.6059,38.926,17.5332";

    private static final String inputRates2 = "1.1787,130.05,1.9558,N/A,25.629,7.4372,N/A,0.85468,361.65,N/A,N/A,N/A," +
            "4.5888,N/A,4.9223,10.205,N/A,N/A,1.0826,148.6,10.427,7.5245,87.1713,N/A,10.0973,1.601,6.1546,1.4804,7.6426,9" +
            ".1656,17084.23,3.8487,87.7305,1360.69,23.6826,4.9877,1.6889,59.342,1.6032,38.832,17.4846";

    private static final String inputRates3 = "1.1767,130.11,1.9558,N/A,25.65,7.4382,N/A,0.85543,359.13,N/A,N/A,N/A,4" +
            ".5691,N/A,4.9227,10.2105,N/A,N/A,1.0838,149,10.4065,7.5353,86.6254,N/A,10.0573,1.5944,6.1009,1.4788,7.6224,9" +
            ".142,17050.24,3.851,87.5905,1354.19,23.6237,4.9733,1.6846,59.12,1.5997,38.743,17.3882";

    private static final String inputRates4 = "1.1775,129.83,1.9558,N/A,25.639,7.4382,N/A,0.85563,357.87,N/A,N/A,N/A," +
            "4.5661,N/A,4.9227,10.2245,N/A,N/A,1.0829,148.2,10.3795,7.532,86.9522,N/A,10.0717,1.5959,6.1029,1.4779,7.6153,9" +
            ".1506,17063.23,3.8524,87.6615,1352.59,23.729,4.9726,1.6906,58.921,1.602,38.704,17.1449";

    private final String[] arrayOfCurrencyRates1 = inputRates1.split(COMMA_DELIMITER);
    private final String[] arrayOfCurrencyRates2 = inputRates2.split(COMMA_DELIMITER);
    private final String[] arrayOfCurrencyRates3 = inputRates3.split(COMMA_DELIMITER);
    private final String[] arrayOfCurrencyRates4 = inputRates4.split(COMMA_DELIMITER);

    protected final List<CurrencyData> expectedRates1 = getExpectedRates(arrayOfCurrencyRates1);
    protected final List<CurrencyData> expectedRates2 = getExpectedRates(arrayOfCurrencyRates2);
    protected final List<CurrencyData> expectedRates3 = getExpectedRates(arrayOfCurrencyRates3);
    protected final List<CurrencyData> expectedRates4 = getExpectedRates(arrayOfCurrencyRates4);

    protected Map<LocalDate, List<CurrencyData>> referenceData = new HashMap<>();

    protected static final String VALID_TEST_RESOURCE = "src/test/resources/eurofxref-hist.csv";

    public CommonTestData()
    {
        referenceData.put(getLocalDateFromString.apply("2021-07-27"), expectedRates1);
        referenceData.put(getLocalDateFromString.apply("2021-07-26"), expectedRates2);
        referenceData.put(getLocalDateFromString.apply("2021-07-23"), expectedRates3);
        referenceData.put(getLocalDateFromString.apply("2021-07-22"), expectedRates4);
    }

    private List<CurrencyData> getExpectedRates(String[] arrayOfCurrencyRates)
    {
        List<CurrencyData> listOfCurrencyData = new ArrayList<>();
        for (int i = 0; i < arrayOfCurrencyRates.length; i++) {
            listOfCurrencyData.add(new CurrencyData(currencies[i], convertToDouble.apply(arrayOfCurrencyRates[i])));
        }
        return listOfCurrencyData;
    }
}
