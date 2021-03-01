package com.dizquestudios.evertectest.core.debts.domain;

import com.dizquestudios.evertectest.core.debts.shared.StringChecker;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.util.Objects;

/**
 * Define the whole domain logic for clients in the debts module.
 *
 * @author Sebastian
 */
public class Client {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record ClientMail(String mail) {

        public static final String MAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

        public ClientMail {
            StringChecker.matchesRegexPattern(MAIL_PATTERN, mail);
        }

    }
    
    public static String ID_FIELD = "id";
    
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.mail);
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.mail, other.mail);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", mail=" + mail + ", name=" + name + '}';
    }
}
