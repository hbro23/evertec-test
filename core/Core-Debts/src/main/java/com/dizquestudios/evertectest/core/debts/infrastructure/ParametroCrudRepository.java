package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sebastian
 */
public interface ParametroCrudRepository extends CrudRepository<Parametro, Integer> {

    @Query(value = "SELECT p.pk,p.id,p.valor FROM parametros AS p WHERE p.id=? AND JSON_VALUE(p.valor,'$.column.name')=?", nativeQuery = true)
    Optional<Parametro> findByIdAndColumName(String id, String nombre);

    List<Parametro> findById(String id);
}
