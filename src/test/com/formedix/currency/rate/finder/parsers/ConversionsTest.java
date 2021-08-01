package com.formedix.currency.rate.finder.parsers;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static com.formedix.currency.rate.finder.parsers.Conversions.convertToBigDecimal;
import static com.formedix.currency.rate.finder.parsers.Conversions.getLocalDateFromString;
import static org.junit.jupiter.api.Assertions.*;

public class ConversionsTest
{
    @Test
    void convertsToBigDecimalValidNumbers()
    {
        //GIVEN
        String n1 = "1.181";
        String n2 = "0.85503";
        String n3 = "1360.75";

        //WHEN / THEN
        assertNotNull(convertToBigDecimal.apply(n1));
        assertNotNull(convertToBigDecimal.apply(n2));
        assertNotNull(convertToBigDecimal.apply(n3));
    }

    @Test
    void convertsToNullForInvalidValue()
    {
        //GIVEN
        String n4 = "-1360.75";
        String na = "N/A";

        //WHEN / THEN
        assertNull(convertToBigDecimal.apply(n4));
        assertNull(convertToBigDecimal.apply(na));
    }

    @Test
    void getsLocalDateTypeForValidDateString()
    {
        //GIVEN
        final String stringDate = "2021-07-27";

        //WHEN / THEN
        assertNotNull(getLocalDateFromString.apply(stringDate));
    }

    @Test
    void failsToParseUnsupportedDateFormat()
    {
        //GIVEN
        final String stringDate = "27-07-2021";

        //WHEN / THEN
        assertThrows(DateTimeParseException.class, () -> getLocalDateFromString.apply(stringDate));
    }
}