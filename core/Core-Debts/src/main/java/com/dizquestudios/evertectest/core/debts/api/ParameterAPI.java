package com.dizquestudios.evertectest.core.debts.api;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.domain.ParameterRepository;
import com.dizquestudios.evertectest.core.debts.shared.API;

/**
 *
 * @author Sebastian
 */
@API
public class ParameterAPI {

    private final ParameterRepository repository;

    public ParameterAPI(ParameterRepository repository) {
        this.repository = repository;
    }

    public List<Parameter> findAll() {
        return repository.findAll();
    }

    public List<Parameter> findAllByEntity(String entity) {
        return repository.findAllByEntity(entity);
    }

    public void save(Parameter parameter) {
        repository.save(parameter);
    }

    public void saveByEntity(List<Parameter> parameters) {
        repository.saveList(parameters);
    }

    public void saveFromFile(File parameters) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Parameter> parms = Arrays.asList(mapper.readValue(parameters, Parameter[].class));
        repository.saveList(parms);
    }
}
