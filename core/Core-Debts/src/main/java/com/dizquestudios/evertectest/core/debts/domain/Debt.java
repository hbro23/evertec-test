package com.dizquestudios.evertectest.core.debts.domain;

import java.time.LocalDateTime;

import org.javamoney.moneta.FastMoney;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import com.dizquestudios.evertectest.core.debts.shared.MoneyChecker;

/**
 *
 * @author Sebastian
 */
public class Debt {
    
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record DebtAmount (FastMoney amount){
        public DebtAmount {
            MoneyChecker.checkGreaterZero(amount);
        }
    }
    
    private String id;
    private String client;
    private DebtAmount amount;
    private LocalDateTime expiryDate;

    public DebtAmount getAmount() {
        return amount;
    }

    public void setAmount(DebtAmount amount) {
        this.amount = amount;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
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

}
