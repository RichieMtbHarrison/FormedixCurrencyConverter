package com.formedix.currency.rate.finder.constants;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public final class Constants
{
    public static final String COMMA_DELIMITER = ",";
    public static final String FORMATTER_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = ofPattern(FORMATTER_PATTERN);

}
