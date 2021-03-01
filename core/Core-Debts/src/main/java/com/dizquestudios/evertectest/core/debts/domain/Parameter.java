package com.dizquestudios.evertectest.core.debts.domain;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.dizquestudios.evertectest.core.debts.shared.IntegerChecker;
import com.dizquestudios.evertectest.core.debts.shared.StringChecker;

/**
 *
 * @author Sebastian
 */
public class Parameter {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record ParameterColumn(@JsonProperty("name") String name,
            @JsonProperty("column") Integer column,
            @JsonProperty("max-length") Integer maxLength) {

        public ParameterColumn   {
            StringChecker.checkEmpty(name);
            IntegerChecker.checkNegative(column);
            IntegerChecker.checkNegative(maxLength);
        }

    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public record ParameterPattern(String pattern) {

        public static final String DEFAULT_PATTERN = ".+";

        public ParameterPattern {
            pattern = StringChecker.ifEmpty(pattern, DEFAULT_PATTERN);
            StringChecker.checkRegexPattern(pattern);
        }

    }

    private String entity;
    private ParameterColumn column;

    @JsonProperty("pattern-validation")
    private ParameterPattern patternValidation;

    /**
     * Get the value of patternValidation
     *
     * @return the value of patternValidation
     */
    public ParameterPattern getPatternValidation() {
        return patternValidation;
    }

    /**
     * Set the value of patternValidation
     *
     * @param patternValidation new value of patternValidation
     */
    public void setPatternValidation(ParameterPattern patternValidation) {
        this.patternValidation = patternValidation;
    }

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.entity);
        hash = 29 * hash + Objects.hashCode(this.column);
        hash = 29 * hash + Objects.hashCode(this.patternValidation);
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
        final Parameter other = (Parameter) obj;
        if (!Objects.equals(this.entity, other.entity)) {
            return false;
        }
        if (!Objects.equals(this.patternValidation, other.patternValidation)) {
            return false;
        }
        return Objects.equals(this.column, other.column);
    }

    @Override
    public String toString() {
        return "Parameter{" + "entity=" + entity + ", column=" + column + ", patternValidation=" + patternValidation + '}';
    }
}
