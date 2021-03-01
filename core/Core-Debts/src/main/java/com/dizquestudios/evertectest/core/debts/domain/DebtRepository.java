package com.dizquestudios.evertectest.core.debts.domain;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sebastian
 */
public interface DebtRepository {

    void saveList(List<Debt> debts, ClientRepository clientRepository);

    List<Debt> findAll(ClientRepository clientRepository);
    
    public Optional<Debt> findDebt(String id, ClientRepository clientRepository);
}
