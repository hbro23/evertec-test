package com.dizquestudios.evertectest.core.debts.domain;

import com.dizquestudios.evertectest.core.debts.shared.IntegerChecker;
import com.dizquestudios.evertectest.core.debts.shared.StringChecker;

/**
 *
 * @author sebas
 */
public class Parameter {

    public record ParameterColumn(String name, Integer column, Integer maxLength) {

        public ParameterColumn   {
            StringChecker.checkEmpty(name);
            IntegerChecker.checkNegative(column);
            IntegerChecker.checkNegative(maxLength);
        }

    }

    public record ParameterPattern(String pattern) {

        public ParameterPattern {
            StringChecker.checkRegexPattern(pattern);
        }

    }

    private String entity;
    private ParameterColumn column;

    /**
     * Get the value of column
     *
     * @return the value of column
     */
    public ParameterColumn getColumn() {
        return column;
    }

    /**
     * Set the value of column
     *
     * @param column new value of column
     */
    public void setColumn(ParameterColumn column) {
        this.column = column;
    }

    /**
     * Get the value of entity
     *
     * @return the value of entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     * Set the value of entity
     *
     * @param entity new value of entity
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }

}
