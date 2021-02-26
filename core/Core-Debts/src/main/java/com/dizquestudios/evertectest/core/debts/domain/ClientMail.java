package com.dizquestudios.evertectest.core.debts.domain;

import java.util.regex.Pattern;

import com.dizquestudios.evertectest.core.debts.shared.StringValueObject;

/**
 *
 * @author Sebastian
 */
public class ClientMail extends StringValueObject {

    public static final String MAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    private static void checkMailFormat(String value) {
        if (!Pattern.matches(MAIL_PATTERN, value)) {
            throw new IllegalArgumentException("Email doesn't have a valid format.");
        }

    }

    public ClientMail(String value) {
        super(value);
        checkMailFormat(value);
    }

}
