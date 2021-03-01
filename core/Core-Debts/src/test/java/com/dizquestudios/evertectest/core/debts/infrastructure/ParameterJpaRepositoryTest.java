package com.dizquestudios.evertectest.core.debts.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.dizquestudios.evertectest.core.debts.domain.Parameter;

/**
 *
 * @author Sebastian
 */
@Transactional
public class ParameterJpaRepositoryTest {

    @Autowired
    private ParameterJpaRepository repository;

    @DisplayName("Save a parameter in the database.")
    void saveParamerter() throws Exception {

        Parameter parameter = new Parameter();
        parameter.setEntity("some-entity");
        parameter.setColumn(new Parameter.ParameterColumn("some-id", 1, 10));

        repository.save(parameter);
    }
}
