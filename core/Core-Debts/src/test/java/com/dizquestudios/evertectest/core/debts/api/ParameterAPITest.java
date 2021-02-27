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

    @Test
    @DisplayName("Save a parameters list from a JSON file.")
    void loadParametersFromFile() throws Exception {
        String data = """
                      [
                          {
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
                              "pattern-validation": "^.{1,10}$"
                          }
                      ]
                      """;

        ParameterRepository repository = mock(ParameterRepository.class);
        ParameterAPI api = new ParameterAPI(repository);

        File tempFile = File.createTempFile("test", "");
        ObjectMapper mapper = new ObjectMapper();
        Files.writeString(tempFile.toPath(), data, StandardOpenOption.APPEND);
        List<Parameter> parameters = Arrays.asList(mapper.readValue(tempFile, Parameter[].class));

        api.saveFromFile(tempFile);
        verify(repository, atLeastOnce()).saveList(parameters);

        tempFile.delete();
    }
}
