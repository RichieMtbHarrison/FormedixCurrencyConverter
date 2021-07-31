package com.formedix.currency.rate.finder.parsers;

import com.formedix.currency.rate.finder.models.CurrencyData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest
{
    private FileParser testee;
    private static final String VALID_TEST_RESOURCE = "src/test/resources/eurofxref-hist.csv";

    @BeforeEach
    void setUp()
    {
        testee = new FileParser();
    }

    @Test
    public void cannotModifyReturnedData()
    {
        //WHEN
        Map<LocalDate, List<CurrencyData>> result = testee.parse(VALID_TEST_RESOURCE);

        //THEN
        assertThrows(UnsupportedOperationException.class, () -> result.clear());
    }

    @Test
    public void parseFileThatDoesExist()
    {
        //WHEN
        Map<LocalDate, List<CurrencyData>> result = testee.parse(VALID_TEST_RESOURCE);

        //THEN
        assertNotNull(result);
        assertEquals(9, result.size());
    }

    @Test
    public void parseFileThatDoesNotExist()
    {
        //GIVEN
        final String currencyDataResource = "eurofref-hist.csv";

        //WHEN
        Map<LocalDate, List<CurrencyData>> result = testee.parse(currencyDataResource);

        //THEN
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}