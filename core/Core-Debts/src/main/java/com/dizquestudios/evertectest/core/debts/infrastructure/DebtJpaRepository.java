package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import com.dizquestudios.evertectest.core.debts.domain.Debt;
import com.dizquestudios.evertectest.core.debts.domain.DebtRepository;

/**
 *
 * @author Sebastian
 */
@Repository
public class DebtJpaRepository implements DebtRepository {

    DeudaCrudRepository crudRepository;

    @Override
    public void saveList(List<Debt> debts, ClientRepository clientRepository) {
        ClientJpaRepository clientJpaRepository = (ClientJpaRepository) clientRepository;

        crudRepository.saveAll(toDeudaList(debts, clientJpaRepository));
    }

    private Deuda toDeuda(Debt debt, ClientJpaRepository clientJpaRepository) {
        Deuda newDeuda = new Deuda();
        newDeuda.setId(debt.getId());
        newDeuda.setMonto(debt.getAmount().getNumber().numberValue(BigDecimal.class));

        Integer pkClient = clientJpaRepository.findPk(debt.getClient()).orElseThrow(() -> {
            return new IllegalArgumentException(
                    String.format("Client doesn't exist. Debt didn't save it. Client: %s | Debt: %s",
                            debt.getClient(), debt.getId()));
        });
        newDeuda.setCliente(pkClient);

        return newDeuda;
    }

    private List<Deuda> toDeudaList(List<Debt> debts, ClientJpaRepository clientJpaRepository) {
        List<Deuda> deudas = debts.parallelStream().map((debt) -> {
            return toDeuda(debt, clientJpaRepository);
        }).collect(Collectors.toList());

        return deudas;
    }

}
