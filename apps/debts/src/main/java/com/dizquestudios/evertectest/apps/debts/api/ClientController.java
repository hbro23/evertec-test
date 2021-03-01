package com.dizquestudios.evertectest.apps.debts.api;

import java.util.List;

import org.springframework.http.MediaType;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dizquestudios.evertectest.core.debts.api.ClientAPI;
import com.dizquestudios.evertectest.core.debts.domain.Client;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
            public record ClientRequest(@JsonProperty("id") String id) {

    }

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

    @GetMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Find client information.")
    @ApiResponse(code = 200, message = "OK")
    public ResponseEntity<Client> findClientById(@RequestBody ClientRequest request) {

        return api.findClient(request.id())
                .map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
