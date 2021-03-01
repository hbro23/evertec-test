package com.dizquestudios.evertectest.apps.debts.api;

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

import com.dizquestudios.evertectest.core.debts.api.ParameterAPI;
import com.dizquestudios.evertectest.core.debts.domain.Parameter;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/parameters")
public class ParameterController {

    private final ParameterAPI api;

    public ParameterController(ParameterAPI api) {
        this.api = api;
    }

    @PostMapping(value = "/of/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<Parameter>> saveFromFile(
            @RequestPart("file") MultipartFile file) throws IOException {

        File paramsFile = File.createTempFile("parms", ".cnf");
        file.transferTo(paramsFile);
        api.saveFromFile(paramsFile);
        paramsFile.delete();

        return new ResponseEntity<>(api.findAll(), HttpStatus.OK);
    }
}
