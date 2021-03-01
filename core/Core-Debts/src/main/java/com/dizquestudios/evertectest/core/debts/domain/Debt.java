package com.dizquestudios.evertectest.core.debts.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.javamoney.moneta.FastMoney;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import com.dizquestudios.evertectest.core.debts.shared.MoneyChecker;
import java.util.Objects;

/**
 *
 * @author Sebastian
 */
public class Debt {
    
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
            public record DebtAmount(FastMoney amount) {

        public DebtAmount {
            MoneyChecker.checkGreaterZero(amount);
        }
    }
    
    public final static String CURRENCY = "CLP";
    public static String CLIENT_FIELD = "client";
    public static String AMOUNT_FIELD = "amount";

    public static String parserExpiryDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString();
    }

    private String id;
    private String client;
    
    
    private FastMoney amount;

    @JsonProperty("expiry-date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    public FastMoney getAmount() {
        return amount;
    }

    public void setAmount(FastMoney amount) {
        this.amount = amount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Get the value of client
     *
     * @return the value of client
     */
    public String getClient() {
        return client;
    }

    /**
     * Set the value of client
     *
     * @param client new value of client
     */
    public void setClient(String client) {
        this.client = client;
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
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.client);
        hash = 97 * hash + Objects.hashCode(this.amount);
        hash = 97 * hash + Objects.hashCode(this.expiryDate);
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
        final Debt other = (Debt) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        return Objects.equals(this.expiryDate, other.expiryDate);
    }

    @Override
    public String toString() {
        return "Debt{" + "id=" + id + ", client=" + client + ", amount=" + amount + ", expiryDate=" + expiryDate + '}';
    }
    
}
