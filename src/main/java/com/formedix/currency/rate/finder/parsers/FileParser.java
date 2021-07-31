package com.formedix.currency.rate.finder.parsers;

import com.formedix.currency.rate.finder.models.CurrencyData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.formedix.currency.rate.finder.constants.Constants.COMMA_DELIMITER;
import static com.formedix.currency.rate.finder.constants.Constants.DATE_FORMATTER;
import static com.formedix.currency.rate.finder.parsers.Conversions.convertToBigDecimal;
import static java.util.Collections.unmodifiableMap;

public final class FileParser
{
    public Map<LocalDate, List<CurrencyData>> parse(String resource)
    {
        Map<LocalDate, List<CurrencyData>> parsedCsvRecords = new HashMap<>();
        String[] currencies = new String[0];
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(resource)))) {
            String line;
            int counter = 1;
            while ((line = br.readLine()) != null) {
                if (1 == counter) {
                    currencies = line.split(COMMA_DELIMITER);
                    counter++;
                } else {
                    String[] recordValues = line.split(COMMA_DELIMITER);
                    parsedCsvRecords.put(getRecordDate.apply(recordValues), addReferenceData(currencies, recordValues));
                }
            }
        } catch (IOException e) {
            //Clear out any records that may have been added prior to unexpected exception.
            parsedCsvRecords.clear();
        }
        return unmodifiableMap(parsedCsvRecords);
    }

    private List<CurrencyData> addReferenceData(String[] currencies, String[] recordValues)
    {
        List<CurrencyData> referenceData = new ArrayList<>();
        for (int i = 1; i < recordValues.length; i++) {
            referenceData.add(new CurrencyData(currencies[i], convertToBigDecimal.apply(recordValues[i])));
        }
        return referenceData;
    }

    private final Function<String[], LocalDate> getRecordDate = (record) -> LocalDate.parse(record[0], DATE_FORMATTER);
}
