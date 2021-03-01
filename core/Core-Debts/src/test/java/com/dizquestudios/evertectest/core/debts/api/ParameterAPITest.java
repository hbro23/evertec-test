package com.dizquestudios.evertectest.core.debts.api;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

import com.dizquestudios.evertectest.core.debts.domain.Parameter;
import com.dizquestudios.evertectest.core.debts.domain.ParameterRepository;

/**
 *
 * @author Sebastian
 */
public final class ParameterAPITest {

    public static String data = """
                      [{
                      		"entity": "Client",
                      		"column": {
                      			"name": "id",
                      			"column": 0,
                      			"max-length": 15
                      		},
                      		"pattern-validation": ""
                      	},
                      	{
                      		"entity": "Client",
                      		"column": {
                      			"name": "name",
                      			"column": 1,
                      			"max-length": 60
                      		},
                      		"pattern-validation": "^.{1,60}$"
                      	},
                      	{
                      		"entity": "Client",
                      		"column": {
                      			"name": "mail",
                      			"column": 2,
                      			"max-length": 60
                      		},
                      		"pattern-validation": ""
                      	},
                      	{
                      		"entity": "Debt",
                      		"column": {
                      			"name": "amount",
                      			"column": 3,
                      			"max-length": 20
                      		},
                      		"pattern-validation": "^\\\\d{1,20}$"
                      	},
                      	{
                      		"entity": "Debt",
                      		"column": {
                      			"name": "id",
                      			"column": 4,
                      			"max-length": 15
                      		},
                      		"pattern-validation": "^.{1,15}$"
                      	},
                      	{
                      		"entity": "Debt",
                      		"column": {
                      			"name": "expiry-date",
                      			"column": 5,
                      			"max-length": 10
                      		},
                      		"pattern-validation": "^.{1,10}$"
                      	}
                      ]
                      """;

    @Test
    @DisplayName("Save a parameters list from a JSON file.")
    void loadParametersFromFile() throws Exception {

        ParameterRepository repository = mock(ParameterRepository.class);
        ParameterAPI api = new ParameterAPI(repository);

        File tempFile = File.createTempFile("test", "");
        Files.writeString(tempFile.toPath(), data, StandardOpenOption.APPEND);
        ObjectMapper mapper = new ObjectMapper();
        List<Parameter> parameters = Arrays.asList(mapper.readValue(tempFile, Parameter[].class));

        api.saveFromFile(tempFile);
        verify(repository, atLeastOnce()).saveList(parameters);

        tempFile.delete();
    }
}
