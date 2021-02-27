package com.dizquestudios.evertectest.core.debts.domain;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sebastian
 */
public interface ParameterRepository {

    public List<Parameter> findAll();

    public List<Parameter> findAllByEntity(String entity);

    public void save(Parameter parameter);

    public void saveList(List<Parameter> parameters);

    public Optional<Parameter> findParameter(String entity, String nameColumn);

}
