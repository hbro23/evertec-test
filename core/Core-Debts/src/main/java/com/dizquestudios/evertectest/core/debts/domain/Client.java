package com.dizquestudios.evertectest.core.debts.domain;

import com.dizquestudios.evertectest.core.debts.shared.StringChecker;

/**
 * Define the whole domain logic for clients in the debts module.
 *
 * @author Sebastian
 */
public class Client {

    public record ClientMail(String mail) {

        public static final String MAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

        public ClientMail {
            StringChecker.matchesRegexPattern(MAIL_PATTERN, mail);
        }

    }

    private String id;

    private ClientMail mail;

    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of mail
     *
     * @return the value of mail
     */
    public ClientMail getMail() {
        return mail;
    }

    /**
     * Set the value of mail
     *
     * @param mail new value of mail
     */
    public void setMail(ClientMail mail) {
        this.mail = mail;
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }

}
