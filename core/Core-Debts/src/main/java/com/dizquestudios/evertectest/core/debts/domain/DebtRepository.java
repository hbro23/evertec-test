package com.dizquestudios.evertectest.core.debts.domain;

import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface DebtRepository {

    void saveList(List<Debt> debts, ClientRepository clientRepository);
}
