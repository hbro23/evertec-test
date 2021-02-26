package com.dizquestudios.evertectest.core.debts.domain;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sebastian
 */
public interface ClientRepository {

    public List<Client> findAll();
    
    public Optional<Client> getClient(String id);
    
    void save(Client client);

    void delete(String id);
}
