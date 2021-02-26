package com.dizquestudios.evertectest.core.debts.api;

import java.util.List;
import com.dizquestudios.evertectest.core.debts.domain.Client;
import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import com.dizquestudios.evertectest.core.debts.shared.API;

/**
 * Public API for Client.
 * @author Sebastian
 */
@API
public class ClientAPI {
    
    private final ClientRepository repository;

    public ClientAPI(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> findAll() {
        return repository.findAll();
    }

}
