package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.domain.ParameterRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 * @author Sebastian
 */
@Repository
public class ParameterJpaRepository implements ParameterRepository {

    public static final String ENTITY_FIELD = "entity";

    private final ParametroCrudRepository crudRepository;
    private final ObjectMapper mapper;

    public ParameterJpaRepository(ParametroCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Parameter> findAll() {
        return toListParameter(crudRepository.findAll());
    }

    @Override
    public List<Parameter> findAllByEntity(String entity) {
        return toListParameter(crudRepository.findById(entity));
    }

    @Override
    public void save(Parameter parameter) {
        crudRepository.save(toParametro(parameter));
    }

    @Override
    public void saveList(List<Parameter> parameters) {
        crudRepository.saveAll(toListParameteros(parameters));
    }

    @Override
    public Optional<Parameter> findParameter(String entity, String nameColumn) {
        return crudRepository.findByIdAndColumName(entity, entity)
                .map(this::toParameter);
    }

    private Parameter toParameter(Parametro parametro) {

        JSONObject value = new JSONObject(parametro.getValor());
        value.put(ENTITY_FIELD, parametro.getId());

        try {
            return mapper.readValue(value.toString(), Parameter.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ParameterJpaRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException(String.format(
                    "Parse Parametro to Parameter error. Exception: %s",
                    ex.getMessage()), ex);
        }
    }

    private Parametro toParametro(Parameter parameter) {
        Parametro newParametro = new Parametro();
        newParametro.setId(parameter.getEntity());

        try {
            String value = mapper.writeValueAsString(parameter);
            JSONObject json = new JSONObject(value);
            json.remove(ENTITY_FIELD);
            newParametro.setValor(json.toString());

            return newParametro;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ParameterJpaRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException(String.format(
                    "Parse Parameter to Parametro error. Exception: %s",
                    ex.getMessage()), ex);
        }

    }

    private List<Parameter> toListParameter(Iterable<Parametro> parametros) {
        return StreamSupport.stream(parametros.spliterator(), true)
                .map(this::toParameter)
                .collect(Collectors.toList());
    }

    private List<Parametro> toListParameteros(List<Parameter> parameters) {
        return parameters.parallelStream()
                .map(this::toParametro)
                .collect(Collectors.toList());
    }
;
}
