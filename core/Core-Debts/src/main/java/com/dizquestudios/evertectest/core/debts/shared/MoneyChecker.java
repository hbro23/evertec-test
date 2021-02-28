package com.dizquestudios.evertectest.core.debts.shared;

import javax.money.MonetaryAmount;

/**
 *
 * @author Sebastian
 */
public class MoneyChecker extends ValueObjectChecker {

    public static void checkNull(MonetaryAmount amount) {
        ValueObjectChecker.checkNull(amount, "Money value is null.");
    }

    public static void checkGreaterZero(MonetaryAmount amount) {
        checkNull(amount);
        if (amount.isNegativeOrZero()) {
            throw new IllegalArgumentException(String.format("Value must be greater than or equal to zero. Value: %d", amount));
        }
    }
}
