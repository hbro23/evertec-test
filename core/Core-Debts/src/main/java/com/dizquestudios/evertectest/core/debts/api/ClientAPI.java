package com.dizquestudios.evertectest.core.debts.api;

import java.util.List;
import java.util.Optional;

import com.dizquestudios.evertectest.core.debts.domain.Client;
import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import com.dizquestudios.evertectest.core.debts.shared.API;

/**
 * Public API for Client.
 *
 * @author Sebastian
 */
@API
public class ClientAPI {

    private final ClientRepository repository;

    public ClientAPI(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> findClient(String id) {
        return this.repository.findClient(id);
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

    public void saveList(List<Client> clients) {
        repository.saveList(clients);
    }

    public ClientRepository getRepository() {
        return repository;
    }
}
