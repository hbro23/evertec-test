package com.dizquestudios.evertectest.core.debts.api;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.javamoney.moneta.FastMoney;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import static org.mockito.Mockito.atLeastOnce;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.dizquestudios.evertectest.core.debts.domain.ClientRepository;
import com.dizquestudios.evertectest.core.debts.domain.Debt;
import com.dizquestudios.evertectest.core.debts.domain.DebtRepository;
import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.domain.ParameterRepository;
import static com.dizquestudios.evertectest.core.debts.domain.Debt.CURRENCY;

/**
 *
 * @author Sebastian
 */
public class DebtAPITest {

    public static final String data = """
                      1037617767SCO;sebastian cardona osorio;sebastian.co.dev@gmail.com;1000000;1037DEUDA2020;01-03-2021
                      1037617767SCO;sebastian cardona osorio;sebastian.co.dev@gmail.com;5000000;1037DEUDA2021;31-08-2021
                      7676171037OCS;clark cardona zapata;nolodude23@gmail.com;5000000;7676DEUDA2021;31-03-2021
                      1037620830SZA;stefanny zapata atencia;stefany.za.ambiental@gmail.com;800000;1037NODEUDA2020;01-04-2021
                      """;

    @Test
    @DisplayName("Save a debts list from a CSV file.")
    void loadDebtsFromFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        ParameterRepository parameterRepository = mock(ParameterRepository.class);
        List<Parameter> parameters = Arrays.asList(mapper.readValue(ParameterAPITest.data, Parameter[].class));
        when(parameterRepository.findAll()).thenReturn(parameters);
        ParameterAPI parameterApi = new ParameterAPI(parameterRepository);

        ClientRepository clientRepository = mock(ClientRepository.class);
        ClientAPI clientApi = new ClientAPI(clientRepository);

        DebtRepository debRepository = mock(DebtRepository.class);
        DebtAPI debtAPI = new DebtAPI(debRepository);

        File debtsFile = File.createTempFile("debts", ".csv");
        Files.writeString(debtsFile.toPath(), data, StandardOpenOption.APPEND);

        Debt debt = new Debt();
        debt.setId("1037DEUDA2020");
        debt.setAmount(FastMoney.of(1000000, CURRENCY));
        debt.setExpiryDate(LocalDate.parse(Debt.parserExpiryDate("01-03-2021")));
        debt.setClient("1037617767SCO");

        Debt debt2 = new Debt();
        debt2.setId("1037DEUDA2021");
        debt2.setAmount(FastMoney.of(5000000, CURRENCY));
        debt2.setExpiryDate(LocalDate.parse(Debt.parserExpiryDate("31-08-2021")));
        debt2.setClient("1037617767SCO");

        Debt debt3 = new Debt();
        debt3.setId("7676DEUDA2021");
        debt3.setAmount(FastMoney.of(5000000, CURRENCY));
        debt3.setExpiryDate(LocalDate.parse(Debt.parserExpiryDate("31-03-2021")));
        debt3.setClient("7676171037OCS");

        Debt debt4 = new Debt();
        debt4.setId("1037NODEUDA2020");
        debt4.setAmount(FastMoney.of(800000, CURRENCY));
        debt4.setExpiryDate(LocalDate.parse(Debt.parserExpiryDate("01-04-2021")));
        debt4.setClient("1037620830SZA");

        List<Debt> debts = List.of(debt, debt2, debt3, debt4);

        debtAPI.loadDebsFromFile(debtsFile, parameterApi, clientApi);
        verify(debRepository, atLeastOnce()).saveList(debts, clientRepository);

        debtsFile.delete();
    }
}
