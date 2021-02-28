package com.dizquestudios.evertectest.core.debts.api;

import com.dizquestudios.evertectest.core.debts.domain.Debt;
import com.dizquestudios.evertectest.core.debts.domain.DebtRepository;
import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.shared.API;
import com.dizquestudios.evertectest.core.debts.shared.StringChecker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Sebastian
 */
@API
public class DebtAPI {
    
    private final DebtRepository repository;

    public DebtAPI(DebtRepository repository) {
        this.repository = repository;
    }

    public void loadDebsFromFile(File debts, List<Parameter> parameters) throws IOException {
        Map<Integer, Parameter> rules = new HashMap<>();
        parameters.parallelStream().forEach((parameter) -> {
            rules.put(parameter.getColumn().column(), parameter);
        });

        Function<String, Debt> lineToParameter = (debtLine) -> {
            Debt newDebt = new Debt();
            String[] fields = debtLine.split(",");
            boolean isValid = rules.entrySet().parallelStream().allMatch((ruleEntry) -> {
                String candidate = fields[ruleEntry.getKey()];
                Parameter parameter = ruleEntry.getValue();
                StringChecker.matchesRegexPattern(parameter.getPatternValidation(), candidate);
                return candidate.length() == parameter.getColumn().maxLength();
            });

            return newDebt;
        };

        List<Debt> validatedDebts = Files.lines(debts.toPath()).parallel()
                .map(lineToParameter).collect(Collectors.toList());
        
        repository.saveList(validatedDebts);
        
    }
}
