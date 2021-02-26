package com.dizquestudios.evertectest.core.debts.shared;

/**
 *
 * @author Sebastian
 */
public class IntegerChecker extends ValueObjectChecker {

    public static void checkNull(Integer value) {
        ValueObjectChecker.checkNull(value, "Integer value is null.");
    }

    public static void checkNegative(Integer value) {
        checkNull(value);
        if (value < 0) {
            throw new IllegalArgumentException(String.format("Value must be greater than zero. Value: %d", value));
        }
    }
}
