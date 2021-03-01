package com.dizquestudios.evertectest.apps.debts.api;

import com.dizquestudios.evertectest.core.debts.api.ClientAPI;
import com.dizquestudios.evertectest.core.debts.api.DebtAPI;
import com.dizquestudios.evertectest.core.debts.api.ParameterAPI;
import com.dizquestudios.evertectest.core.debts.domain.Debt;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/debts")
public class DebtController {

    private final DebtAPI api;
    private final ParameterAPI parameterAPI;
    private final ClientAPI clientAPI;

    public DebtController(DebtAPI api, ParameterAPI parameterAPI, ClientAPI clientAPI) {
        this.api = api;
        this.parameterAPI = parameterAPI;
        this.clientAPI = clientAPI;
    }

    @PostMapping(value = "/of/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Save all debts and clients (if it's neccesary) from csv.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Invalid debt in file.")})
    public ResponseEntity<List<Debt>> saveFromFile(
            @RequestPart("file") MultipartFile file) throws IOException {

        File paramsFile = File.createTempFile("debts", ".csv");
        file.transferTo(paramsFile);
        api.loadDebsFromFile(paramsFile, parameterAPI, clientAPI);
        paramsFile.delete();

        return new ResponseEntity<>(api.findAll(clientAPI), HttpStatus.OK);
    }
}
