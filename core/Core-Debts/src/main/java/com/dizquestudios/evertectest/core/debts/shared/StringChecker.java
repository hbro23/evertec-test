package com.dizquestudios.evertectest.core.debts.shared;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author Sebastian
 */
public class StringChecker extends ValueObjectChecker {

    public static String ifNull(String value, String defaultValue) {
        if (defaultValue == null) {
            return defaultValue;
        }

        return value;
    }

    public static String ifEmpty(String value, String defaultValue) {
        value = ifNull(value, "");

        if (value.isEmpty()) {
            return defaultValue;
        }

        return value;
    }

    public static void checkNull(String value) {
        ValueObjectChecker.checkNull(value, "String value is null.");
    }

    public static void checkEmpty(String value) {
        checkNull(value);

        if (value.isEmpty()) {
            throw new IllegalArgumentException("String value is empty.");
        }
    }

    public static void checkRegexPattern(String pattern) {

        try {
            Pattern.compile(pattern);
        } catch (PatternSyntaxException ex) {
            Logger.getLogger(StringChecker.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new IllegalArgumentException(String.format("Regex pattern is not valid. Pattern: '%s'", pattern));
        }

    }

    public static void matchesRegexPattern(String pattern, String candidate) {
        checkRegexPattern(pattern);

        if (!Pattern.matches(pattern, candidate)) {
            throw new IllegalArgumentException(
                    String.format("Value doesn't match. Pattern: '%s' | Value: '%s' |",
                            pattern, candidate));
        }
    }

}
