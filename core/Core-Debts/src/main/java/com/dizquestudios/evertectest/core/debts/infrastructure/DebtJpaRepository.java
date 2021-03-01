package com.dizquestudios.evertectest.core.debts.infrastructure;

import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Repository;

import com.dizquestudios.evertectest.core.debts.domain.Debt;
import com.dizquestudios.evertectest.core.debts.domain.DebtRepository;
import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import static com.dizquestudios.evertectest.core.debts.domain.Debt.CURRENCY;
import java.util.stream.StreamSupport;

/**
 *
 * @author Sebastian
 */
@Repository
public class DebtJpaRepository implements DebtRepository {

    DeudaCrudRepository crudRepository;

    public DebtJpaRepository(DeudaCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Debt> findAll(ClientRepository clientRepository) {
        ClientJpaRepository clientJpaRepository = (ClientJpaRepository) clientRepository;

        return toDebtList(crudRepository.findAll(), clientJpaRepository);
    }

    @Override
    public void saveList(List<Debt> debts, ClientRepository clientRepository) {
        ClientJpaRepository clientJpaRepository = (ClientJpaRepository) clientRepository;

        crudRepository.saveAll(toDeudaList(debts, clientJpaRepository));
    }

    private Debt toDebt(Deuda deuda, ClientJpaRepository clientJpaRepository) {
        Debt newDebt = new Debt();
        newDebt.setId(deuda.getId());
        newDebt.setAmount(FastMoney.of(deuda.getMonto(), CURRENCY));
        newDebt.setExpiryDate(deuda.getVencimiento());

        String client = clientJpaRepository.findId(deuda.getCliente()).orElseThrow(() -> {
            return new IllegalArgumentException(
                    String.format("Client doesn't exist. Client: %d | Debt: %s",
                            deuda.getCliente(), deuda.getId()));
        });
        newDebt.setClient(client);

        return newDebt;
    }

    private List<Debt> toDebtList(Iterable<Deuda> deudas, ClientJpaRepository clientJpaRepository) {

        List<Debt> debts = StreamSupport.stream(deudas.spliterator(), true)
                .map((deuda) -> {
                    return toDebt(deuda, clientJpaRepository);
                }).collect(Collectors.toList());

        return debts;
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
        newDeuda.setVencimiento(debt.getExpiryDate());

        return newDeuda;
    }

    private List<Deuda> toDeudaList(Iterable<Debt> debts, ClientJpaRepository clientJpaRepository) {
        List<Deuda> deudas = StreamSupport.stream(debts.spliterator(), true)
                .map((debt) -> {
                    return toDeuda(debt, clientJpaRepository);
                }).collect(Collectors.toList());

        return deudas;
    }

}
