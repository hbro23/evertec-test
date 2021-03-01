package com.dizquestudios.evertectest.apps.debts.api;

import com.dizquestudios.evertectest.core.debts.api.ClientAPI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizquestudios.evertectest.core.debts.domain.Client;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientAPI api;

    public ClientController(ClientAPI api) {
        this.api = api;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("List all clients.")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<List<Client>> getAll() {
        return new ResponseEntity<>(api.findAll(), HttpStatus.OK);
    }
}
