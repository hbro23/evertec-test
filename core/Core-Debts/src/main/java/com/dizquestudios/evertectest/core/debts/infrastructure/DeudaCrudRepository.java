package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sebastian
 */
public interface DeudaCrudRepository extends CrudRepository<Deuda, Integer> {

    Optional<Deuda> findById(String id);
}
