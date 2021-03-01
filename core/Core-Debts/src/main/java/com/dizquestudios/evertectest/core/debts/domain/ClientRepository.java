package com.dizquestudios.evertectest.core.debts.domain;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sebastian
 */
public interface ClientRepository {

    public List<Client> findAll();

    public Optional<Client> findClient(String id);

    public List<Client> findById(List<Client> clients);

    void save(Client client);

    void saveList(List<Client> clients);

}
