package com.dizquestudios.evertectest.core.debts.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.javamoney.moneta.FastMoney;
import org.zalando.jackson.datatype.money.MoneyModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.dizquestudios.evertectest.core.debts.domain.Client;
import static com.dizquestudios.evertectest.core.debts.domain.Client.ID_FIELD;
import com.dizquestudios.evertectest.core.debts.domain.Debt;
import static com.dizquestudios.evertectest.core.debts.domain.Debt.AMOUNT_FIELD;
import static com.dizquestudios.evertectest.core.debts.domain.Debt.CLIENT_FIELD;
import static com.dizquestudios.evertectest.core.debts.domain.Debt.EXPIRY_DATE_FIELD;
import com.dizquestudios.evertectest.core.debts.domain.DebtRepository;
import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.shared.API;
import com.dizquestudios.evertectest.core.debts.shared.StringChecker;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;

/**
 *
 * @author Sebastian
 */
@API
public class DebtAPI {

    public final static String CURRENCY = "CLP";
    private final static String CURRENCY_FIELD = "currency";

    record ClientAndDebt(Client client, Debt debt) {

    }

    private final DebtRepository repository;
    private final ObjectMapper mapper;

    public DebtAPI(DebtRepository repository) {
        this.repository = repository;
        this.mapper = new ObjectMapper()
                .registerModule(new MoneyModule()
                        .withMonetaryAmount(FastMoney::of));
        this.mapper.registerModule(new JavaTimeModule());
    }

    private List<ClientAndDebt> extractClientsAndDebts(Stream<String> lines,
            List<Parameter> parameters) {

        Map<Integer, Parameter> rules = new HashMap<>();
        parameters.parallelStream().forEach((parameter) -> {
            rules.put(parameter.getColumn().column(), parameter);
        });

        Function<String, ClientAndDebt> lineToDebts = (debtLine) -> {
            Debt newDebt;
            Client client = null;
            String[] fields = debtLine.split(";");
            JSONObject dataDebt = new JSONObject();
            JSONObject dataClient = new JSONObject();
            Map<String, JSONObject> entities = Map.of(Client.class.getSimpleName(),
                    dataClient, Debt.class.getSimpleName(), dataDebt);

            try {
                rules.entrySet().parallelStream().forEach((ruleEntry) -> {
                    String candidate = fields[ruleEntry.getKey()];
                    Parameter parameter = ruleEntry.getValue();
                    StringChecker.matchesRegexPattern(parameter.getPatternValidation().pattern(), candidate);
                    if (candidate.length() > parameter.getColumn().maxLength()) {
                        throw new IllegalArgumentException(String.format(
                                "Unexpected length. It must be lower than %d but it was %d.",
                                parameter.getColumn().maxLength(), candidate.length()));
                    }
                    entities.get(parameter.getEntity())
                            .put(parameter.getColumn().name(), candidate);
                });
            } catch (IllegalArgumentException ex) {
                String msg = String.format("Line doesn't match the set parameters. Line: '%s' | Error: %s",
                        debtLine, ex.getMessage());
                throw new IllegalArgumentException(msg, ex);
            }

            dataDebt.put(CLIENT_FIELD, dataClient.get(ID_FIELD));
//            dataDebt.put(EXPIRY_DATE_FIELD, Debt.parserExpiryDate(dataDebt.getString(EXPIRY_DATE_FIELD)));
            JSONObject debtAmount = new JSONObject();
            debtAmount.put(CURRENCY_FIELD, CURRENCY);
            debtAmount.put(AMOUNT_FIELD, new BigDecimal(dataDebt.getString(AMOUNT_FIELD)));
            dataDebt.put(AMOUNT_FIELD, debtAmount);
            
            try {
                newDebt = mapper.readValue(dataDebt.toString(), Debt.class);
                client = mapper.readValue(dataClient.toString(), Client.class);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(DebtAPI.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalArgumentException(String.format(
                        "Parse error. Exception: %s", ex.getMessage()), ex);
            }

            return new ClientAndDebt(client, newDebt);
        };

        return lines.map(lineToDebts).collect(Collectors.toList());
    }

    public void loadDebsFromFile(File debts, ParameterAPI parameterAPI,
            ClientAPI clientAPI) throws IOException {
        List<Parameter> parameters = parameterAPI.findAll();
        List<ClientAndDebt> validatedDebts = extractClientsAndDebts(
                Files.lines(debts.toPath()).parallel(), parameters);

        List<Client> clients = validatedDebts.parallelStream()
                .map(ClientAndDebt::client).distinct().collect(Collectors.toList());

        clientAPI.saveList(clients);

        List<Debt> newDebts = validatedDebts.parallelStream()
                .map(ClientAndDebt::debt).collect(Collectors.toList());

        repository.saveList(newDebts, clientAPI.getRepository());
    }
}
