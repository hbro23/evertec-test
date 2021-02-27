package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.dizquestudios.evertectest.core.debts.domain.Client;
import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;

/**
 * Memory stored repository. It's necessary to run quickly tests.
 *
 * @author Sebastian
 */
@Repository
public class ClientRepositoryMemory implements ClientRepository {

    private final Map<String, Client> clients = new HashMap<>();

    @Override
    public List<Client> findAll() {
        return clients.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findClient(String id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public void save(Client client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void delete(String id) {
        findClient(id).orElseThrow(() -> {
            return new IllegalArgumentException(String.format("Client with id: '%s' not found.", id));
        });

        clients.remove(id);
    }

}
