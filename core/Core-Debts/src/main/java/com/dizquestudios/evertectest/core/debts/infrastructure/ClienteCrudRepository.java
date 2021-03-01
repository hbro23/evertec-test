package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sebastian
 */
public interface ClienteCrudRepository extends CrudRepository<Cliente, Integer> {

    List<Cliente> findByIdIn(List<String> ids);
    
    Optional<Cliente> findById(String id);
}
