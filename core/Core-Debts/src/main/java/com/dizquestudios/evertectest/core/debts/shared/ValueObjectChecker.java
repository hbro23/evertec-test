package com.dizquestudios.evertectest.core.debts.shared;

/**
 *
 * @author Sebastian
 */
public class ValueObjectChecker {

    public static void checkNull(Object value, String msgError) {
        if (value == null) {
            throw new IllegalArgumentException(msgError);
        }
    }
}
