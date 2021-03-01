package com.dizquestudios.evertectest.core.debts.infrastructure;

import com.dizquestudios.evertectest.core.debts.domain.Client;
import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastian
 */
@Repository
public class ClientJpaRepository implements ClientRepository {

    private static final ConcurrentMap<String, Integer> cachePKs = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Integer, String> cacheIDs = new ConcurrentHashMap<>();

    private final ClienteCrudRepository crudRepository;

    public ClientJpaRepository(ClienteCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Client> findAll() {
        return toListClient(crudRepository.findAll());
    }

    @Override
    public Optional<Client> findClient(String id) {
        return crudRepository.findById(id).map(this::toClient);
    }

    @Override
    public List<Client> findById(List<Client> clients) {
        List<String> ids = clients.parallelStream().map(Client::getId)
                .collect(Collectors.toList());

        return toListClient(crudRepository.findByIdIn(ids));
    }

    @Override
    public void save(Client client) {
        crudRepository.save(toCliente(client));
    }

    @Override
    public void saveList(List<Client> clients) {
        crudRepository.saveAll(toListCliente(clients));
    }

    Optional<Integer> findPk(Client client) {
        return findPk(client.getId());
    }

    Optional<Integer> findPk(String id) {
        return Optional.ofNullable(cachePKs.computeIfAbsent(id, (key) -> {
            return crudRepository.findById(key).map((cliente) -> {
                return cliente.getPk();
            }).orElse(null);
        }));
    }

    Optional<String> findId(Cliente cliente) {
        return findId(cliente.getPk());
    }

    Optional<String> findId(Integer pk) {

        return Optional.ofNullable(cacheIDs.computeIfAbsent(pk, (key) -> {
            return crudRepository.findById(key).map((cliente) -> {
                return cliente.getId();
            }).orElse(null);
        }));
    }

    private Client toClient(Cliente cliente) {
        Client newClient = new Client();
        newClient.setId(cliente.getId());
        newClient.setName(cliente.getNombre());
        newClient.setMail(new Client.ClientMail(cliente.getCorreo()));

        cachePKs.put(cliente.getId(), cliente.getPk());
        cacheIDs.put(cliente.getPk(), cliente.getId());

        return newClient;
    }

    private List<Client> toListClient(Iterable<Cliente> clientes) {
        return StreamSupport.stream(clientes.spliterator(), true)
                .map(this::toClient)
                .collect(Collectors.toList());
    }

    private Cliente toCliente(Client client) {
        Cliente newCliente = new Cliente();
        newCliente.setNombre(client.getName());
        newCliente.setId(client.getId());
        newCliente.setCorreo(client.getMail().mail());

        findPk(client).ifPresent((pk) -> {
            newCliente.setPk(pk);
            cachePKs.put(client.getId(), pk);
            cacheIDs.put(pk, client.getId());
        });

        return newCliente;
    }

    private List<Cliente> toListCliente(List<Client> clients) {
        return StreamSupport.stream(clients.spliterator(), true)
                .map(this::toCliente)
                .collect(Collectors.toList());
    }
}
